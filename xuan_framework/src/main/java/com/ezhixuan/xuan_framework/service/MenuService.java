package com.ezhixuan.xuan_framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
    ResponseResult<List<MenuVo>> queryList(MenuListDTO menuListDTO);

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    ResponseResult<String> addMenu(Menu menu);

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    ResponseResult updateMenu(Menu menu);

    /**
     * 删除菜单
     * @param ids
     * @return
     */
    ResponseResult<String> delete(List<Long> ids);

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
}
