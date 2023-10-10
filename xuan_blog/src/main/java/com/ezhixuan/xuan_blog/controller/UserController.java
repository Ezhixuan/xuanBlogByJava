package com.ezhixuan.xuan_blog.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.dto.user.UserInfoDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserRegisterDTO;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.UserInfoVo;
import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import com.ezhixuan.xuan_framework.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @program: xuanBlog
 * @description: 用户控制器
 * @author: Mr.Xuan
 * @create: 2023-09-28 23:05
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户控制器")
public class UserController {
  @Resource private UserService userService;

  @Log(businessName = "查询用户信息")
  @ApiOperation("查询用户信息")
  @GetMapping("/userInfo")
  public ResponseResult<UserInfoVo> userInfo() {
    return userService.selectUser();
  }

  @Log(businessName = "更新用户信息")
  @ApiOperation("更新用户信息")
  @PutMapping("/userInfo")
  public ResponseResult<String> updateUserInfo(@RequestBody @Validated(Update.class) UserInfoDTO userInfoDTO) {
    return userService.updateUser(userInfoDTO);
  }

  @Log(businessName = "注册")
  @ApiOperation("注册")
  @PostMapping("/register")
  public ResponseResult<String> register(@RequestBody @Validated(Insert.class) UserRegisterDTO userRegisterDTO){
    return userService.insertUser(userRegisterDTO);
  }
}
