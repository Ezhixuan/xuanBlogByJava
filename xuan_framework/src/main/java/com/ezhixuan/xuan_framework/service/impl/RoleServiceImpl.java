package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.dao.RoleDao;
import com.ezhixuan.xuan_framework.domain.dto.role.RolePageDTO;
import com.ezhixuan.xuan_framework.domain.dto.role.RoleSaveDTO;
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
   * @param rolePageDTO
   * @return
   */
  @Override
  public ResponseResult<PageVo> queryList(RolePageDTO rolePageDTO) {
    // 1. 参数校验
    rolePageDTO.check();
    // 2. 构建查询
    LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    // 2.1 如果角色名存在，根据角色名模糊查询
    roleLambdaQueryWrapper.like(
        StringUtils.hasText(rolePageDTO.getRoleName()),
        Role::getRoleName,
        rolePageDTO.getRoleName());
    // 2.2 如果角色状态信息存在，根据角色状态查询,否则查询正常状态的角色
    if (StringUtils.hasText(rolePageDTO.getStatus())) {
      roleLambdaQueryWrapper.eq(Role::getStatus, rolePageDTO.getStatus());
    } else {
      roleLambdaQueryWrapper.eq(Role::getStatus, CommonConstant.STATUS_NORMAL);
    }
    // 2.3 根据角色创建时间排序
    roleLambdaQueryWrapper.orderByDesc(Role::getCreateTime);
    // 3. 分页查询
    Page<Role> rolePage = new Page<>(rolePageDTO.getPageNum(), rolePageDTO.getPageSize());
    page(rolePage, roleLambdaQueryWrapper);
    // 4. 封装返回
    List<RoleVo> roleVos = BeanUtil.copyBeanList(rolePage.getRecords(), RoleVo.class);
    PageVo pageVo = new PageVo(roleVos, rolePage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 修改角色状态
   *
   * @param role
   * @return
   */
  @Override
  public ResponseResult<String> updateStatus(Role role) {
    // 1. 参数校验
    Long roleId = role.getId();
    String status = role.getStatus();
    if (roleId == null) {
      throw new BaseException("角色id不能为空");
    } else if (!CommonConstant.STATUS_NORMAL.equals(status)
        && !CommonConstant.STATUS_DISABLE.equals(status)) {
      throw new BaseException("角色状态不合法");
    }
    // 2. 如果角色和用户有绑定则不能停用
    int count =
        roleMenuService.count(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, roleId));
    if (count > 0 && CommonConstant.STATUS_DISABLE.equals(status)) {
      throw new BaseException("该角色已经绑定用户，不能停用");
    }
    // 3. 执行修改
    updateById(role);
    // 4. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 新增角色
   *
   * @param roleDTO
   * @return
   */
  @Override
  public ResponseResult<String> saveByDto(RoleSaveDTO roleDTO) {
    // 1. 参数校验
    if (roleDTO == null) {
      throw new BaseException("角色信息不能为空");
    }
    // 2. 保存
    Role role = BeanUtil.copyBean(roleDTO, Role.class);
    save(role);
    // 3. 建立角色菜单关系
    if (!roleDTO.getMenuIds().isEmpty()) {
      roleDTO
          .getMenuIds()
          .forEach(menuId -> roleMenuService.save(new RoleMenu(role.getId(), menuId)));
    }
    // 4. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 获取角色信息
   *
   * @param id
   * @return
   */
  @Override
  public ResponseResult<RoleVo> get(Long id) {
    // 1. 参数校验
    if (id == null) {
      throw new BaseException("角色id不能为空");
    }
    // 2. 查询角色信息
    Role role = getById(id);
    if (role == null) {
      throw new BaseException("角色不存在");
    }
    // 3. 封装返回
    RoleVo roleVo = BeanUtil.copyBean(role, RoleVo.class);
    return ResponseResult.okResult(roleVo);
  }

  /**
   * 修改角色信息
   *
   * @param roleDTO
   * @return
   */
  @Override
  @Transactional()
  public ResponseResult<String> updateByDto(RoleSaveDTO roleDTO) {
    // 1. 参数校验
    if (roleDTO == null || roleDTO.getId() == null) {
      throw new BaseException("角色信息不能为空");
    }
    // 2. 修改角色信息
    updateById(roleDTO);
    // 3. 删除角色菜单关系
    if (!ObjectUtils.isEmpty(roleDTO.getMenuIds())) {
      // 如果不为空则表示需要进行修改
      // 3.1 删除角色菜单关系
      roleMenuService.remove(
          Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, roleDTO.getId()));
      // 3.2 重新建立角色菜单关系
      roleDTO
          .getMenuIds()
          .forEach(menuId -> roleMenuService.save(new RoleMenu(roleDTO.getId(), menuId)));
    }
    // 4. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 删除角色
   *
   * @param ids
   * @return
   */
  @Override
  @Transactional
  public ResponseResult<String> delete(List<Long> ids) {
    // 1. 校验参数
    if (ObjectUtils.isEmpty(ids)) {
      throw new BaseException("角色id不能为空");
    }
    // 2. 查询角色是否处于停用状态
    ids.forEach(
        id -> {
          Role role = getById(id);
          if (role == null) {
            throw new BaseException("角色不存在");
          }
          if (CommonConstant.STATUS_NORMAL.equals(role.getStatus())) {
            throw new BaseException("角色未处于停用状态，不能删除");
          }
          // 3. 删除角色菜单关系
          roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, id));
        });
    // 4. 执行删除
    removeByIds(ids);
    // 5. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 查询所有角色
   *
   * @return
   */
  @Override
  public ResponseResult<List<Role>> queryAll() {
    // 1. 查询所有状态正常的角色
    List<Role> roles =
        list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, CommonConstant.STATUS_NORMAL));
    // 2. 返回
    return ResponseResult.okResult(roles);
  }
}
