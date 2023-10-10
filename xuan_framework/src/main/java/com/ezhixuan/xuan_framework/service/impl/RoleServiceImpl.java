package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.dao.RoleDao;
import com.ezhixuan.xuan_framework.domain.dto.role.RoleDTO;
import com.ezhixuan.xuan_framework.domain.dto.role.RolePageDTO;
import com.ezhixuan.xuan_framework.domain.dto.role.RoleStatusDTO;
import com.ezhixuan.xuan_framework.domain.entity.Role;
import com.ezhixuan.xuan_framework.domain.entity.RoleMenu;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.role.RoleVo;
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.service.RoleMenuService;
import com.ezhixuan.xuan_framework.service.RoleService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author Ezhixuan
 * @since 2023-10-01 00:21:14
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

  @Resource private RoleMenuService roleMenuService;

  /**
   * 查询角色列表
   *
   * @param rolePageDTO 查询条件
   * @return 角色列表
   */
  @Override
  public ResponseResult<PageVo> selectRolePageSys(RolePageDTO rolePageDTO) {
    rolePageDTO.check();
    // 构建查询
    LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    roleLambdaQueryWrapper.like(
        StringUtils.hasText(rolePageDTO.getRoleName()),
        Role::getRoleName,
        rolePageDTO.getRoleName());
    if (StringUtils.hasText(rolePageDTO.getStatus())) {
      roleLambdaQueryWrapper.eq(Role::getStatus, rolePageDTO.getStatus());
    } else {
      roleLambdaQueryWrapper.eq(Role::getStatus, CommonConstant.STATUS_NORMAL);
    }
    roleLambdaQueryWrapper.orderByDesc(Role::getCreateTime);
    // 分页查询
    Page<Role> rolePage = new Page<>(rolePageDTO.getPageNum(), rolePageDTO.getPageSize());
    page(rolePage, roleLambdaQueryWrapper);
    // 封装返回
    List<RoleVo> roleVos = BeanUtil.copyBeanList(rolePage.getRecords(), RoleVo.class);
    PageVo pageVo = new PageVo(roleVos, rolePage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 获取角色信息
   *
   * @param id
   * @return
   */
  @Override
  public ResponseResult<RoleVo> selectRoleByIdSys(Long id) {
    Role role = getById(id);
    RoleVo roleVo = BeanUtil.copyBean(role, RoleVo.class);
    return ResponseResult.okResult(roleVo);
  }

  /**
   * 查询所有角色
   *
   * @return 角色列表
   */
  @Override
  public ResponseResult<List<RoleVo>> selectRoleListSys() {
    List<Role> roles =
        list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, CommonConstant.STATUS_NORMAL));
    List<RoleVo> roleVos = BeanUtil.copyBeanList(roles, RoleVo.class);
    return ResponseResult.okResult(roleVos);
  }

  /**
   * 修改角色状态
   *
   * @param roleStatusDto 角色信息
   * @return 处理结果
   */
  @Override
  public ResponseResult<String> updateRoleStatusSys(RoleStatusDTO roleStatusDto) {
    // 如果角色和用户有绑定则不能停用
    int count =
        roleMenuService.count(
            Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, roleStatusDto.getId()));
    if (count > 0 && CommonConstant.STATUS_DISABLE.equals(roleStatusDto.getStatus())) {
      throw new BaseException("该角色已经绑定用户，不能停用");
    }
    Role role = BeanUtil.copyBean(roleStatusDto, Role.class);
    updateById(role);
    return ResponseResult.SUCCESS;
  }

  /**
   * 修改角色信息
   *
   * @param roleDTO 角色信息
   * @return 处理结果
   */
  @Override
  @Transactional()
  public ResponseResult<String> updateRoleSys(RoleDTO roleDTO) {
    Role role = BeanUtil.copyBean(roleDTO, Role.class);
    // 修改角色信息
    updateById(role);
    // 删除角色菜单关系
    if (!ObjectUtils.isEmpty(roleDTO.getMenuIds())) {
      roleMenuService.remove(
          Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, roleDTO.getId()));
      // 重新建立角色菜单关系
      roleDTO
          .getMenuIds()
          .forEach(menuId -> roleMenuService.save(new RoleMenu(roleDTO.getId(), menuId)));
    }
    return ResponseResult.SUCCESS;
  }

  /**
   * 新增角色
   *
   * @param roleDTO
   * @return
   */
  @Override
  public ResponseResult<String> insertRoleSys(RoleDTO roleDTO) {
    Role role = BeanUtil.copyBean(roleDTO, Role.class);
    save(role);
    // 建立角色菜单关系
    if (!roleDTO.getMenuIds().isEmpty()) {
      roleDTO
          .getMenuIds()
          .forEach(menuId -> roleMenuService.save(new RoleMenu(role.getId(), menuId)));
    }
    return ResponseResult.SUCCESS;
  }

  /**
   * 删除角色
   *
   * @param ids 角色id
   * @return 处理结果
   */
  @Override
  @Transactional
  public ResponseResult<String> deleteRoleByIdSys(List<Long> ids) {
    // 查询角色是否处于停用状态
    ids.forEach(
        id -> {
          Role role = getById(id);
          if (ObjectUtils.isEmpty(id)) {
            throw new BaseException("角色不存在");
          }
          if (CommonConstant.STATUS_NORMAL.equals(role.getStatus())) {
            throw new BaseException("角色未处于停用状态，不能删除");
          }
          // 删除角色菜单关系
          roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, id));
        });
    removeByIds(ids);
    return ResponseResult.SUCCESS;
  }
}
