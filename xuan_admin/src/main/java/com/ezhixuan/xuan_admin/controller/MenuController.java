package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.domain.dto.menu.MenuListDTO;
import com.ezhixuan.xuan_framework.domain.entity.Menu;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.menu.MenuTreeVo;
import com.ezhixuan.xuan_framework.domain.vo.menu.MenuVo;
import com.ezhixuan.xuan_framework.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @program: xuanBlog
 * @description: 系统菜单控制器
 * @author: Mr.Xuan
 * @create: 2023-10-02 21:35
 */
@RestController
@RequestMapping("system/menu")
@Api(tags = "系统菜单控制器")
public class MenuController {

  @Resource private MenuService menuService;

  @GetMapping("list")
  @ApiOperation("获取系统菜单")
  public ResponseResult<List<MenuVo>> queryList(MenuListDTO menuListDTO) {
    return menuService.queryList(menuListDTO);
  }

  @PostMapping
  @ApiOperation("新增系统菜单")
  public ResponseResult<Boolean> addMenu(@RequestBody Menu menu) {
    return ResponseResult.okResult(menuService.save(menu));
  }

  @ApiOperation("获取系统菜单详情")
  @GetMapping(value = "/{menuId}")
  public ResponseResult<Menu> getInfo(@PathVariable Long menuId) {
    return ResponseResult.okResult(menuService.getById(menuId));
  }

  @ApiModelProperty("修改系统菜单")
  @PutMapping
  public ResponseResult edit(@RequestBody Menu menu) {
    return menuService.updateMenu(menu);
  }

  @ApiModelProperty("删除系统菜单")
  @DeleteMapping({"{menuId}"})
  public ResponseResult<String> delete(@PathVariable("menuId") List<Long> ids){
    return menuService.delete(ids);
  }

  @GetMapping("treeselect")
  @ApiOperation("获取系统菜单树")
  public ResponseResult<List<MenuTreeVo>> queryTree(){
    return menuService.queryTree();
  }
  
  @GetMapping("roleMenuTreeselect/{id}")
  @ApiOperation("获取角色菜单树")
    public ResponseResult<List<MenuTreeVo>> queryRoleMenuTree(@PathVariable Long id){
        return menuService.queryRoleMenuTree(id);
    }
}
