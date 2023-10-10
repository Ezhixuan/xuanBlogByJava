package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.dto.role.RoleDTO;
import com.ezhixuan.xuan_framework.domain.dto.role.RolePageDTO;
import com.ezhixuan.xuan_framework.domain.dto.role.RoleStatusDTO;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.role.RoleVo;
import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Select;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import com.ezhixuan.xuan_framework.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @program: xuanBlog
 * @description: 角色控制器
 * @author: Mr.Xuan
 * @create: 2023-10-03 14:16
 */
@Api(tags = "角色控制器")
@RestController
@RequestMapping("system/role")
public class RoleController {

  @Resource private RoleService roleService;

  @Log(businessName = "获取角色列表")
  @GetMapping("/list")
  @ApiOperation("获取角色列表")
  public ResponseResult<PageVo> queryList(RolePageDTO rolePageDTO) {
    return roleService.selectRolePageSys(rolePageDTO);
  }

  @Log(businessName = "修改角色状态")
  @PutMapping("changeStatus")
  @ApiOperation("修改角色状态")
  public ResponseResult<String> updateStatus(
      @Validated(Update.class) RoleStatusDTO rolesStatusDto) {
    return roleService.updateRoleStatusSys(rolesStatusDto);
  }

  @Log(businessName = "新增角色")
  @PostMapping
  @ApiOperation("新增角色")
  public ResponseResult<String> save(@RequestBody @Validated(Insert.class) RoleDTO roleDTO) {
    return roleService.insertRoleSys(roleDTO);
  }

  @Log(businessName = "获取角色信息")
  @GetMapping("/{id}")
  @ApiOperation("获取角色信息")
  public ResponseResult<RoleVo> get(
      @PathVariable @Validated @NotNull(message = "角色id不能为空", groups = Select.class) Long id) {
    return roleService.selectRoleByIdSys(id);
  }

  @Log(businessName = "修改角色信息")
  @PutMapping
  @ApiOperation("修改角色信息")
  public ResponseResult<String> update(@RequestBody @Validated(Update.class) RoleDTO roleDTO) {
    return roleService.updateRoleSys(roleDTO);
  }

  @Log(businessName = "删除角色")
  @DeleteMapping("/{id}")
  @ApiOperation("删除角色")
  public ResponseResult<String> delete(
      @PathVariable("id") @Validated @NotEmpty(message = "请选择要删除的角色", groups = Update.class)
          List<Long> ids) {
    return roleService.deleteRoleByIdSys(ids);
  }

  @Log(businessName = "获取所有角色")
  @GetMapping("/listAllRole")
  @ApiOperation("获取所有角色")
  public ResponseResult<List<RoleVo>> queryAll() {
    return roleService.selectRoleListSys();
  }
}
