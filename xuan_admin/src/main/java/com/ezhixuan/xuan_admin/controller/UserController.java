package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.domain.dto.user.UserPageDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserSaveDTO;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.UserQueryVo;
import com.ezhixuan.xuan_framework.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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
@RequestMapping("system/user")
public class UserController {

    @Resource private UserService userService;


    @GetMapping("list")
    @ApiOperation("获取用户列表")
    public ResponseResult<PageVo> queryList(UserPageDTO userDto){
        return userService.queryList(userDto);
    }

    @PostMapping()
    @ApiOperation("新增用户")
    public ResponseResult<String> save(@RequestBody UserSaveDTO userDto){
        return userService.saveByDto(userDto);
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除用户")
    public ResponseResult<String> delete(@PathVariable("id") List<Long> ids){
        return userService.delete(ids);
    }

    @GetMapping("{id}")
    @ApiOperation("获取用户信息")
    public ResponseResult<UserQueryVo> query(@PathVariable("id") Long id){
        return userService.query(id);
    }

    @PutMapping()
    @ApiOperation("修改用户信息")
    public ResponseResult<String> update(@RequestBody UserSaveDTO userDto){
        return userService.updateByDto(userDto);
    }
}
