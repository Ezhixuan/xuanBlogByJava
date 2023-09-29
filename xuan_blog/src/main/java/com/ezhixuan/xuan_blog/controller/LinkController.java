package com.ezhixuan.xuan_blog.controller;

import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.link.LinkListVo;
import com.ezhixuan.xuan_framework.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: xuanBlog
 * @description: 友链控制器
 * @author: Mr.Xuan
 * @create: 2023-09-25 16:45
 */
@RestController
@RequestMapping("/link")
@Api(tags = "友链控制器")
public class LinkController {

    @Resource private LinkService linkService;

    @GetMapping("/getAllLink")
    @ApiOperation("获取所有友链")
    public ResponseResult<List<LinkListVo>> queryAllLink(){
        return linkService.queryAllLink();
    }
}
