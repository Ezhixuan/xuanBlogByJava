package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.domain.dto.user.UserLoginDTO;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.menu.RoutersVo;
import com.ezhixuan.xuan_framework.domain.vo.user.SysUserInfo;
import com.ezhixuan.xuan_framework.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @program: xuanBlog
 * @description: 用户控制器
 * @author: Mr.Xuan
 * @create: 2023-09-30 20:19
 */
@Api(tags = "用户控制器")
@RestController
@RequestMapping()
public class UserController {

    @Resource private LoginService loginService;

    @ApiOperation("后台登录")
    @PostMapping("/user/login")
    public ResponseResult<Map<String, String>> login(@RequestBody UserLoginDTO userLoginDTO) {
        return loginService.login(userLoginDTO);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("getInfo")
    public ResponseResult<SysUserInfo> getInfo(){
        return loginService.getInfo();
    }

    @ApiOperation("路由")
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        return loginService.getRouters();
    }
}
