package com.ezhixuan.xuan_blog.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.category.CategoryVo;
import com.ezhixuan.xuan_framework.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: xuanBlog
 * @description: 分类控制器
 * @author: Mr.Xuan
 * @create: 2023-09-24 17:33
 */
@RestController
@Api(tags = "分类控制器")
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @Log(businessName = "分类列表")
    @ApiOperation("分类列表")
    @GetMapping("/getCategoryList")
    public ResponseResult<List<CategoryVo>> getCategoryList(){
        List<CategoryVo> categoryList = categoryService.getCategoryList();
        return ResponseResult.okResult(categoryList);
    }
}
