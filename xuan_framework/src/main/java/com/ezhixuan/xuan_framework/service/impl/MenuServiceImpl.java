package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.dao.MenuDao;
import com.ezhixuan.xuan_framework.domain.dto.menu.MenuListDTO;
import com.ezhixuan.xuan_framework.domain.entity.Menu;
import com.ezhixuan.xuan_framework.domain.entity.RoleMenu;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.menu.MenuTreeVo;
import com.ezhixuan.xuan_framework.domain.vo.menu.MenuVo;
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.service.MenuService;
import com.ezhixuan.xuan_framework.service.RoleMenuService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @program: xuanBlog
 * @description: 菜单实现类
 * @author: Mr.Xuan
 * @create: 2023-10-02 21:41
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

  @Resource private RoleMenuService roleMenuService;

  /**
   * 查询菜单列表
   *
   * @param menuListDTO
   * @return
   */
  @Override
  public ResponseResult<List<MenuVo>> queryList(MenuListDTO menuListDTO) {
    // 1. 构建查询条件
    LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    // 1.1 如果菜单名存在则模糊查询菜单名
    menuLambdaQueryWrapper.like(
        StringUtils.hasText(menuListDTO.getMenuName()),
        Menu::getMenuName,
        menuListDTO.getMenuName());
    // 1.2 如果状态存在根据状态查询
    menuLambdaQueryWrapper.eq(
        StringUtils.hasText(menuListDTO.getStatus()), Menu::getStatus, menuListDTO.getStatus());
    // 1.3 根据id编号排序
    menuLambdaQueryWrapper.orderByAsc(Menu::getId);
    // 2. 查询
    List<Menu> list = list(menuLambdaQueryWrapper);
    // 3. 封装返回
    List<MenuVo> menuVos = BeanUtil.copyBeanList(list, MenuVo.class);
    return ResponseResult.okResult(menuVos);
  }

  /**
   * 新增菜单
   *
   * @param menu
   * @return
   */
  @Override
  public ResponseResult<String> addMenu(Menu menu) {
    // 1. 执行新增
    save(menu);
    // 2. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 修改菜单
   *
   * @param menu
   * @return
   */
  @Override
  public ResponseResult updateMenu(Menu menu) {
    // 1. 校验参数
    if (menu == null || ObjectUtils.isEmpty(menu.getId())) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 修改时不能将上级菜单修改成自己
    if (menu.getId().equals(menu.getParentId())) {
      return ResponseResult.errorResult(500, "修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
    }
    // 3. 执行
    updateById(menu);
    // 4. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 删除菜单
   *
   * @param ids
   * @return
   */
  @Override
  public ResponseResult<String> delete(List<Long> ids) {
    // 1. 校验参数
    if (ObjectUtils.isEmpty(ids)) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 删除菜单的角色的对应关系
    ids.forEach(
        id -> roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getMenuId, id)));
    // 3. 执行删除
    removeByIds(ids);
    // 4. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 查询菜单树
   *
   * @return
   */
  @Override
  public ResponseResult<List<MenuTreeVo>> queryTree() {
    // 1. 查询出所有菜单
    List<Menu> menus = list();
    // 2. 构建
    menus = buildTreeImpl(menus, CommonConstant.ROOT);
    // 3. 封装返回
    List<MenuTreeVo> menuTreeVos = copyName(menus);
    return ResponseResult.okResult(menuTreeVos);
  }

  /**
   * 为菜单树的菜单名赋值
   *
   * @param menus
   * @return
   */
  private List<MenuTreeVo> copyName(List<Menu> menus) {
    if (ObjectUtils.isEmpty(menus)) {
      return null;
    }
    return menus.stream()
        .map(
            menu -> {
              MenuTreeVo menuTreeVo = BeanUtil.copyBean(menu, MenuTreeVo.class);
              menuTreeVo.setLabel(menu.getMenuName());
              menuTreeVo.setChildren(copyName(menu.getChildren()));
              return menuTreeVo;
            })
        .collect(Collectors.toList());
  }

  //  @Override
  //  public ResponseResult<List<MenuTreeVo>> queryTree() {
  //    // Xuan TODO 添加角色只能由超级管理员分配
  //    // 1. 获取当前用户id
  //    Long userId = UserUtils.getUser().getId();
  //    // 2. 查询出所有菜单
  //    List<Menu> menus = getBaseMapper().queryRootMenuByUserId(userId);
  //    // 3. 构建
  //    menus = buildTreeImpl(menus);
  //    // 4. 封装返回
  // Xuan TODO 实际上MenuTreeVo的菜单名是label而Menu是menuName，所以需要特殊赋值
  //    List<MenuTreeVo> menuTreeVos = BeanUtil.copyBeanList(menus, MenuTreeVo.class);
  //    return ResponseResult.okResult(menuTreeVos);
  //  }

  /**
   * 构建根目录
   *
   * @param menus
   * @return
   */
  @Override
  public List<Menu> buildTree(List<Menu> menus) {
    return buildTreeImpl(menus, CommonConstant.ROOT);
  }

  /**
   * 构建树实现
   *
   * @param menus
   * @param parentId
   * @return
   */
  private List<Menu> buildTreeImpl(List<Menu> menus, Long parentId) {
    return menus.stream()
        .filter(menu -> menu.getParentId().equals(parentId))
        .peek(menu -> menu.setChildren(buildTreeImpl(menus, menu.getId())))
        .collect(Collectors.toList());
  }

  /**
   * 查询角色菜单树
   *
   * @param id
   * @return
   */
  @Override
  public ResponseResult<List<MenuTreeVo>> queryRoleMenuTree(Long id) {
    // 1. 校验参数
    if (id == null) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 查询该角色下的所有菜单
    List<Menu> menus = getBaseMapper().queryRootMenuByUserId(id);
    // 3. 构建菜单树
    menus = buildTreeImpl(menus, CommonConstant.ROOT);
    // 3. 封装返回
    List<MenuTreeVo> menuTreeVos = copyName(menus);
    return ResponseResult.okResult(menuTreeVos);
  }
}
