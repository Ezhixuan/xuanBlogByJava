package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.domain.dto.category.CategoryPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Category;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.category.CategoryVo;
import com.ezhixuan.xuan_framework.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    
    @GetMapping("list")
    @ApiOperation("获取分类列表")
    public ResponseResult<PageVo> queryList(CategoryPageDTO categoryDTO){
        return categoryService.queryList(categoryDTO);
    }

    @PostMapping
    @ApiOperation("新增分类")
    public ResponseResult<String> save(@RequestBody Category category){
        categoryService.save(category);
        return ResponseResult.SUCCESS;
    }

    @GetMapping("{id}")
    @ApiOperation("根据id查询分类")
    public ResponseResult<Category> query(@PathVariable Long id){
        return ResponseResult.okResult(categoryService.getById(id));
    }

    @PutMapping()
    @ApiOperation("修改分类")
    public ResponseResult<String> update(Category category){
        categoryService.updateById(category);
        return ResponseResult.SUCCESS;
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除分类")
    public ResponseResult<String> delete(@PathVariable("id") List<Long> ids){
        return categoryService.delete(ids);
    }
}
