package com.ezhixuan.xuan_blog.controller;

import com.ezhixuan.xuan_framework.domain.dto.login.UserLoginDTO;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.service.BlogLoginService;
import io.swagger.annotations.Api;
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

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserLoginDTO userLoginDTO){
        return blogLoginService.login(userLoginDTO);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
