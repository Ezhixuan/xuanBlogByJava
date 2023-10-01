package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.category.CategoryVo;
import com.ezhixuan.xuan_framework.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: xuanBlog
 * @description: 菜单控制器
 * @author: Mr.Xuan
 * @create: 2023-10-01 16:15
 */

@Api(tags = "菜单控制器")
@RestController
@RequestMapping("content/category")
public class CategoryController {

    @Resource private CategoryService categoryService;

    @ApiOperation("获取所有分类列表")
    @GetMapping("listAllCategory")
    public ResponseResult<List<CategoryVo>> listAllCategory(){
        return categoryService.listAllCategory();
    }

    @PreAuthorize("@ps.hasPermisson('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        categoryService.export(response);
    }
}
