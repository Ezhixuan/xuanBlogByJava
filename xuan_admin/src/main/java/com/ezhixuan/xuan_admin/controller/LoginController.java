package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.dto.user.UserLoginDTO;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.menu.RoutersVo;
import com.ezhixuan.xuan_framework.domain.vo.user.SysUserInfo;
import com.ezhixuan.xuan_framework.handler.validated.Select;
import com.ezhixuan.xuan_framework.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: xuanBlog
 * @description: 登录控制器
 * @author: Mr.Xuan
 * @create: 2023-10-03 16:38
 */
@Api(tags = "登录控制器")
@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @Log(businessName = "后台用户登录")
    @ApiOperation("后台用户登录")
    @PostMapping("/user/login")
    public ResponseResult<Map<String, String>> userLoginSys(@RequestBody @Validated(Select.class) UserLoginDTO userLoginDTO) {
        return loginService.userLoginSys(userLoginDTO);
    }

    @Log(businessName = "后台用户退出登录")
    @ApiOperation("后台用户退出登录")
    @PostMapping("/user/logout")
    public ResponseResult<String> userLogoutSys() {
        return loginService.userLogoutSys();
    }

    @Log(businessName = "获取用户信息")
    @ApiOperation("获取用户信息")
    @GetMapping("/getInfo")
    public ResponseResult<SysUserInfo> selectUserInfoSys(){
        return loginService.selectUserInfoSys();
    }

    @Log(businessName = "获取用户路由")
    @ApiOperation("获取用户路由")
    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> selectUserRoutersSys(){
        return loginService.selectUserRoutersSys();
    }
}
