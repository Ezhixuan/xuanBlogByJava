package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.dto.category.CategoryDTO;
import com.ezhixuan.xuan_framework.domain.dto.category.CategoryPageDTO;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.category.CategoryVo;
import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import com.ezhixuan.xuan_framework.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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

  @Log(businessName = "获取所有分类列表")
  @ApiOperation("获取所有分类列表")
  @GetMapping("/listAllCategory")
  public ResponseResult<List<CategoryVo>> selectCategoryListSys() {
    return categoryService.selectCategoryListSys();
  }

  @Log(businessName = "导出分类列表")
  @PreAuthorize("@ps.hasPermisson('content:category:export')")
  @GetMapping("/export")
  public void exportExcelSys(HttpServletResponse response) {
    categoryService.exportExcelSys(response);
  }

  @Log(businessName = "获取分类列表")
  @GetMapping("/list")
  @ApiOperation("获取分类列表")
  public ResponseResult<PageVo> selectCategoryPageSys(CategoryPageDTO categoryDTO) {
    return categoryService.selectCategoryPageSys(categoryDTO);
  }

  @Log(businessName = "新增分类")
  @PostMapping
  @ApiOperation("新增分类")
  public ResponseResult<String> insertCategorySys(
      @RequestBody @Validated(Insert.class) CategoryDTO categoryDTO) {
    return categoryService.insertCategorySys(categoryDTO);
  }

  @Log(businessName = "根据id查询分类")
  @GetMapping("/{id}")
  @ApiOperation("根据id查询分类")
  public ResponseResult<CategoryVo> selectCategoryByIdSys(
      @PathVariable @Validated @NotEmpty(message = "查询分类id不能为空", groups = Insert.class) Long id) {
    return categoryService.selectCategoryByIdSys(id);
  }

  @Log(businessName = "修改分类")
  @PutMapping
  @ApiOperation("修改分类")
  public ResponseResult<String> updateCategoryByIdSys(
      @RequestBody @Validated(Update.class) CategoryDTO categoryDTO) {
    return categoryService.updateCategoryByIdSys(categoryDTO);
  }

  @Log(businessName = "删除分类")
  @DeleteMapping("/{id}")
  @ApiOperation("删除分类")
  public ResponseResult<String> deleteCategorySys(
      @PathVariable("id") @Validated @NotEmpty(message = "请选择要删除的分类", groups = Insert.class)
          List<Long> ids) {
    return categoryService.deleteCategoryByIdSys(ids);
  }
}
