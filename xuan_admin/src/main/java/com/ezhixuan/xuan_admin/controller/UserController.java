package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.dto.user.UserDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserPageDTO;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.UserQueryVo;
import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Select;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import com.ezhixuan.xuan_framework.service.UserService;
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
 * @description: 用户控制器
 * @author: Mr.Xuan
 * @create: 2023-09-30 20:19
 */
@Api(tags = "用户控制器")
@RestController
@RequestMapping("system/user")
public class UserController {

  @Resource private UserService userService;

  @Log(businessName = "获取用户列表")
  @GetMapping("/list")
  @ApiOperation("获取用户列表")
  public ResponseResult<PageVo> selectUserPageSys(UserPageDTO userDto) {
    return userService.selectUserPageSys(userDto);
  }

  @Log(businessName = "新增用户")
  @PostMapping
  @ApiOperation("新增用户")
  public ResponseResult<String> insertUserSys(
      @RequestBody @Validated(Insert.class) UserDTO userDto) {
    return userService.insertUserSys(userDto);
  }

  @Log(businessName = "删除用户")
  @DeleteMapping("/{id}")
  @ApiOperation("删除用户")
  public ResponseResult<String> deleteUserByIdSys(
      @PathVariable("id") @NotEmpty(message = "请选择需要删除的用户", groups = Update.class) List<Long> ids) {
    return userService.deleteUserByIdSys(ids);
  }

  @Log(businessName = "获取用户信息")
  @GetMapping("/{id}")
  @ApiOperation("获取用户信息")
  public ResponseResult<UserQueryVo> selectUserByIdSys(
      @PathVariable("id") @NotNull(message = "用户id不能为空", groups = Select.class) Long id) {
    return userService.selectUserByIdSys(id);
  }

  @Log(businessName = "修改用户信息")
  @PutMapping
  @ApiOperation("修改用户信息")
  public ResponseResult<String> updateUserSys(
      @RequestBody @Validated(Update.class) UserDTO userDto) {
    return userService.updateUserSys(userDto);
  }
}
