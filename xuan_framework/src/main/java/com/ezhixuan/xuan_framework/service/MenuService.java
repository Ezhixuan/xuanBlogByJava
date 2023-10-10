package com.ezhixuan.xuan_framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezhixuan.xuan_framework.domain.dto.menu.MenuDTO;
import com.ezhixuan.xuan_framework.domain.entity.Menu;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.dto.menu.MenuListDTO;
import com.ezhixuan.xuan_framework.domain.vo.menu.MenuTreeVo;
import com.ezhixuan.xuan_framework.domain.vo.menu.MenuVo;

import java.util.List;

public interface MenuService extends IService<Menu> {
    /**
     * 查询菜单列表
     * @param menuListDTO
     * @return
     */
    ResponseResult<List<MenuVo>> selectMenuList(MenuListDTO menuListDTO);

    /**
     * 新增菜单
     * @param menuDto
     * @return
     */
    ResponseResult<String> insertMenu(MenuDTO menuDto);

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    ResponseResult<String> updateMenu(MenuDTO menu);

    /**
     * 删除菜单
     * @param ids
     * @return
     */
    ResponseResult<String> deleteMenuById(List<Long> ids);

    /**
     * 查询菜单树
     * @return
     */
    ResponseResult<List<MenuTreeVo>> queryTree();

    /**
     * 构建菜单树
     * @param menus
     * @return
     */
    List<Menu> buildTree(List<Menu> menus);

    /**
     * 查询角色菜单树
     *
     * @param id
     * @return
     */
    ResponseResult<List<MenuTreeVo>> queryRoleMenuTree(Long id);

    /**
     * 根据菜单id查询菜单详情
     * @param menuId
     * @return
     */
    ResponseResult<MenuVo> selectMenuById(Long menuId);
}
