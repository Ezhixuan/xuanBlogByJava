package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.domain.dto.role.RolePageDTO;
import com.ezhixuan.xuan_framework.domain.dto.role.RoleSaveDTO;
import com.ezhixuan.xuan_framework.domain.entity.Role;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.role.RoleVo;
import com.ezhixuan.xuan_framework.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
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

    @GetMapping("list")
    @ApiOperation("获取角色列表")
    public ResponseResult<PageVo> queryList(RolePageDTO rolePageDTO){
        return roleService.queryList(rolePageDTO);
    }

    @PutMapping("changeStatus")
    @ApiOperation("修改角色状态")
    public ResponseResult<String> updateStatus(Role role){
        return roleService.updateStatus(role);
    }
    
    @PostMapping
    @ApiOperation("新增角色")
    public ResponseResult<String> save(@RequestBody RoleSaveDTO roleDTO){
        return roleService.saveByDto(roleDTO);
    }

    @GetMapping("{id}")
    @ApiOperation("获取角色信息")
    public ResponseResult<RoleVo> get(@PathVariable Long id){
        return roleService.get(id);
    }

    @PutMapping()
    @ApiOperation("修改角色信息")
    public ResponseResult<String> update(@RequestBody RoleSaveDTO roleDTO){
        return roleService.updateByDto(roleDTO);
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除角色")
    public ResponseResult<String> delete(@PathVariable("id") List<Long> ids){
        return roleService.delete(ids);
    }

    @GetMapping("listAllRole")
    @ApiOperation("获取所有角色")
    public ResponseResult<List<Role>> queryAll(){
        return roleService.queryAll();
    }
}
