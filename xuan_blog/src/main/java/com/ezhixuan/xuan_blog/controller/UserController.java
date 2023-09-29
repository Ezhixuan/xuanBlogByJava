package com.ezhixuan.xuan_blog.controller;

import com.ezhixuan.xuan_framework.domain.dto.user.UserInfoDTO;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.UserInfoVo;
import com.ezhixuan.xuan_framework.service.UserService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
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
    @Resource
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult<UserInfoVo> userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    public ResponseResult<String> updateUserInfo(@RequestBody UserInfoDTO userInfoDTO){
        return userService.updateUserInfo(userInfoDTO);
  }
}
