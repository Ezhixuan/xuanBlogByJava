package com.ezhixuan.xuan_blog.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.dto.user.UserLoginDTO;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.BlogUserLoginVo;
import com.ezhixuan.xuan_framework.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: xuanBlog
 * @description: 登录控制器
 * @author: Mr.Xuan
 * @create: 2023-09-27 19:01
 */
@RestController
@Api(tags = "登录控制器")
public class BlogLoginController {

    @Resource
    private BlogLoginService blogLoginService;

    @Log(businessName = "登录")
    @PostMapping("/login")
    @ApiOperation("登录")
    public ResponseResult<BlogUserLoginVo> login(@RequestBody UserLoginDTO userLoginDTO){
        return blogLoginService.login(userLoginDTO);
    }

    @Log(businessName = "退出")
    @PostMapping("/logout")
    @ApiOperation("退出")
    public ResponseResult<String> logout(){
        return blogLoginService.logout();
    }
}
