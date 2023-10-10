package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.dto.menu.MenuDTO;
import com.ezhixuan.xuan_framework.domain.dto.menu.MenuListDTO;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.menu.MenuTreeVo;
import com.ezhixuan.xuan_framework.domain.vo.menu.MenuVo;
import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Select;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import com.ezhixuan.xuan_framework.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
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

  @Log(businessName = "获取系统菜单")
  @GetMapping("/list")
  @ApiOperation("获取系统菜单")
  public ResponseResult<List<MenuVo>> selectMenuList(MenuListDTO menuListDTO) {
    return menuService.selectMenuList(menuListDTO);
  }

  @Log(businessName = "新增系统菜单")
  @PostMapping
  @ApiOperation("新增系统菜单")
  public ResponseResult<String> insertMenu(@RequestBody @Validated(Insert.class) MenuDTO menuDto) {
    return menuService.insertMenu(menuDto);
  }

  @Log(businessName = "获取系统菜单详情")
  @ApiOperation("获取系统菜单详情")
  @GetMapping(value = "/{menuId}")
  public ResponseResult<MenuVo> selectMenuById(@PathVariable @Validated @NotNull(message = "菜单id不能为空", groups = Select.class) Long menuId) {
    return menuService.selectMenuById(menuId);
  }

  @Log(businessName = "修改系统菜单")
  @ApiModelProperty("修改系统菜单")
  @PutMapping
  public ResponseResult<String> updateMenu(@RequestBody @Validated(Update.class) MenuDTO menuDto) {
    return menuService.updateMenu(menuDto);
  }

  @Log(businessName = "删除系统菜单")
  @ApiModelProperty("删除系统菜单")
  @DeleteMapping({"/{menuId}"})
  public ResponseResult<String> deleteMenuById(
      @PathVariable("menuId") @Validated @NotEmpty(message = "请选择需要删除的菜单", groups = Update.class)
          List<Long> ids) {
    return menuService.deleteMenuById(ids);
  }

  @Log(businessName = "获取系统菜单树")
  @GetMapping("/treeselect")
  @ApiOperation("获取系统菜单树")
  public ResponseResult<List<MenuTreeVo>> queryTree() {
    return menuService.queryTree();
  }

  @Log(businessName = "获取角色菜单树")
  @GetMapping("/roleMenuTreeselect/{id}")
  @ApiOperation("获取角色菜单树")
  public ResponseResult<List<MenuTreeVo>> queryRoleMenuTree(
      @PathVariable @Validated @NotNull(message = "菜单id不能为空", groups = Select.class) Long id) {
    return menuService.queryRoleMenuTree(id);
  }
}
