package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.dao.MenuDao;
import com.ezhixuan.xuan_framework.domain.dto.menu.MenuDTO;
import com.ezhixuan.xuan_framework.domain.dto.menu.MenuListDTO;
import com.ezhixuan.xuan_framework.domain.entity.Menu;
import com.ezhixuan.xuan_framework.domain.entity.RoleMenu;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.menu.MenuTreeVo;
import com.ezhixuan.xuan_framework.domain.vo.menu.MenuVo;
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
   * @param menuListDTO 查询条件
   * @return 菜单列表
   */
  @Override
  public ResponseResult<List<MenuVo>> selectMenuList(MenuListDTO menuListDTO) {
    // 构建查询条件
    LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    menuLambdaQueryWrapper.like(
        StringUtils.hasText(menuListDTO.getMenuName()),
        Menu::getMenuName,
        menuListDTO.getMenuName());
    menuLambdaQueryWrapper.eq(
        StringUtils.hasText(menuListDTO.getStatus()), Menu::getStatus, menuListDTO.getStatus());
    menuLambdaQueryWrapper.orderByAsc(Menu::getId);
    List<Menu> list = list(menuLambdaQueryWrapper);
    //  封装返回
    List<MenuVo> menuVos = BeanUtil.copyBeanList(list, MenuVo.class);
    return ResponseResult.okResult(menuVos);
  }

  /**
   * 根据菜单id查询菜单详情
   *
   * @param menuId
   * @return
   */
  @Override
  public ResponseResult<MenuVo> selectMenuById(Long menuId) {
    Menu menu = getById(menuId);
    MenuVo menuVo = BeanUtil.copyBean(menu, MenuVo.class);
    return ResponseResult.okResult(menuVo);
  }

  /**
   * 修改菜单
   *
   * @param menuDto 菜单信息
   * @return 结果
   */
  @Override
  public ResponseResult updateMenu(MenuDTO menuDto) {
    // 修改时不能将上级菜单修改成自己
    if (menuDto.getId().equals(menuDto.getParentId())) {
      return ResponseResult.errorResult(500, "修改菜单'" + menuDto.getMenuName() + "'失败，上级菜单不能选择自己");
    }
    Menu menu = BeanUtil.copyBean(menuDto, Menu.class);
    updateById(menu);
    return ResponseResult.SUCCESS;
  }

  /**
   * 新增菜单
   *
   * @param menuDto 菜单信息
   * @return 结果
   */
  @Override
  public ResponseResult<String> insertMenu(MenuDTO menuDto) {
    Menu menu = BeanUtil.copyBean(menuDto, Menu.class);
    save(menu);
    return ResponseResult.SUCCESS;
  }

  /**
   * 删除菜单
   *
   * @param ids 菜单id
   * @return 结果
   */
  @Override
  public ResponseResult<String> deleteMenuById(List<Long> ids) {
    // 删除菜单时需要删除角色菜单关联表中的数据
    ids.forEach(
        id -> roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getMenuId, id)));
    removeByIds(ids);
    return ResponseResult.SUCCESS;
  }

  /**
   * 查询菜单树
   *
   * @return 菜单树
   */
  @Override
  public ResponseResult<List<MenuTreeVo>> queryTree() {
    List<Menu> menus = list();
    // 构建
    menus = buildTreeImpl(menus, CommonConstant.ROOT);
    List<MenuTreeVo> menuTreeVos = copyName(menus);
    return ResponseResult.okResult(menuTreeVos);
  }

  /**
   * 为菜单树的菜单名赋值
   *
   * @param menus 菜单列表
   * @return 菜单树
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

  /**
   * 构建根目录
   *
   * @param menus 菜单列表
   * @return 菜单树
   */
  @Override
  public List<Menu> buildTree(List<Menu> menus) {
    return buildTreeImpl(menus, CommonConstant.ROOT);
  }

  /**
   * 构建树实现
   *
   * @param menus 菜单列表
   * @param parentId 父菜单id
   * @return 菜单树
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
   * @param id 角色id
   * @return 菜单树
   */
  @Override
  public ResponseResult<List<MenuTreeVo>> queryRoleMenuTree(Long id) {
    // 2. 查询该角色下的所有菜单
    List<Menu> menus = getBaseMapper().queryRootMenuByUserId(id);
    // 3. 构建菜单树
    menus = buildTreeImpl(menus, CommonConstant.ROOT);
    // 3. 封装返回
    List<MenuTreeVo> menuTreeVos = copyName(menus);
    return ResponseResult.okResult(menuTreeVos);
  }
}
