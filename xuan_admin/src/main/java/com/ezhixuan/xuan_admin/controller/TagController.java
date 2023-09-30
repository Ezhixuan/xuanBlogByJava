package com.ezhixuan.xuan_admin.controller;


import com.ezhixuan.xuan_framework.domain.entity.Tag;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.service.TagService;
import io.swagger.annotations.Api;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: xuanBlog
 * @description: 标签控制器
 * @author: Mr.Xuan
 * @create: 2023-09-30 17:37
 */
@Api(tags = "标签控制器")
@RestController
@RequestMapping("/tag")
public class TagController {
    
    
    @Resource private TagService tagService;
    
    @GetMapping("list")
    public ResponseResult<List<Tag>> list(){
        return ResponseResult.okResult(tagService.list());
    }
}
