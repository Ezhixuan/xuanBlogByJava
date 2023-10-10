package com.ezhixuan.xuan_framework.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.dao.CategoryDao;
import com.ezhixuan.xuan_framework.domain.dto.category.CategoryDTO;
import com.ezhixuan.xuan_framework.domain.dto.category.CategoryPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Article;
import com.ezhixuan.xuan_framework.domain.entity.Category;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.category.CategoryVo;
import com.ezhixuan.xuan_framework.domain.vo.category.ExcelCategoryVo;
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.service.ArticleService;
import com.ezhixuan.xuan_framework.service.CategoryService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import com.ezhixuan.xuan_framework.utils.WebUtils;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-09-24 17:36:29
 */
@Service("categoryService")
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category>
    implements CategoryService {

  @Resource private ArticleService articleService;

  /**
   * 分类列表 只展示有正式发布文章的分类
   *
   * @return 正式发布的文章分类
   */
  @Override
  public ResponseResult<List<CategoryVo>> selectCategoryList() {
    return ResponseResult.okResult(getBaseMapper().getCategoryList());
  }

  /**
   * 展示所有发布的分类
   *
   * @return 所有文章分类
   */
  @Override
  public ResponseResult<List<CategoryVo>> selectCategoryListSys() {
    List<Category> list = list();
    // 封装返回
    List<CategoryVo> categoryVos = BeanUtil.copyBeanList(list, CategoryVo.class);
    return ResponseResult.okResult(categoryVos);
  }

  /**
   * 分类列表
   *
   * @param categoryDTO 分类dto
   * @return 分类列表
   */
  @Override
  public ResponseResult<PageVo> selectCategoryPageSys(CategoryPageDTO categoryDTO) {
    categoryDTO.check();
    // 构建查询
    LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
    categoryLambdaQueryWrapper.like(
        StringUtils.hasText(categoryDTO.getName()), Category::getName, categoryDTO.getName());
    if (StringUtils.hasText(categoryDTO.getStatus())) {
      categoryLambdaQueryWrapper.eq(Category::getStatus, categoryDTO.getStatus());
    } else {
      categoryLambdaQueryWrapper.eq(Category::getStatus, CommonConstant.STATUS_NORMAL);
    }
    categoryLambdaQueryWrapper.orderByAsc(Category::getId);
    // 分页查询
    Page<Category> categoryPage = new Page<>(categoryDTO.getPageNum(), categoryDTO.getPageSize());
    page(categoryPage, categoryLambdaQueryWrapper);
    // 封装返回
    List<CategoryVo> categoryVos =
        BeanUtil.copyBeanList(categoryPage.getRecords(), CategoryVo.class);
    PageVo pageVo = new PageVo(categoryVos, categoryPage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 删除分类
   *
   * @param ids
   * @return
   */
  @Override
  @Transactional
  public ResponseResult<String> deleteCategoryByIdSys(List<Long> ids) {
    // 查询该分类下是否有文章
    ids.forEach(
        id -> {
          int count =
              articleService.count(Wrappers.<Article>lambdaQuery().eq(Article::getCategoryId, id));
          if (count > 0) {
            throw new BaseException("分类" + id + "下有文章，不能删除");
          }
        });
    // 删除分类
    removeByIds(ids);
    return ResponseResult.SUCCESS;
  }

  /**
   * 导出excel
   *
   * @param response 响应
   */
  @Override
  public void exportExcelSys(HttpServletResponse response) {
    try {
      // 设置下载返回头
      WebUtils.setDownLoadHeader("分类.xlsx", response);
      List<Category> list = list();
      List<ExcelCategoryVo> excelCategoryVos = BeanUtil.copyBeanList(list, ExcelCategoryVo.class);
      // 将数据写入excel中
      EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class)
          .autoCloseStream(Boolean.FALSE)
          .sheet("分类导出")
          .doWrite(excelCategoryVos);
    } catch (Exception e) {
      // 如果出现异常 重置response
      response.reset();
      ResponseResult<Object> result = ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
      String jsonStr = JSONUtil.toJsonStr(result);
      WebUtils.renderString(response, jsonStr);
    }
  }

  /**
   * 新增分类
   *
   * @param categoryDTO
   * @return
   */
  @Override
  public ResponseResult<String> insertCategorySys(CategoryDTO categoryDTO) {
    Category category = BeanUtil.copyBean(categoryDTO, Category.class);
    save(category);
    return ResponseResult.SUCCESS;
  }

  /**
   * 修改分类
   *
   * @param categoryDTO
   * @return
   */
  @Override
  public ResponseResult<String> updateCategoryByIdSys(CategoryDTO categoryDTO) {
    Category category = BeanUtil.copyBean(categoryDTO, Category.class);
    updateById(category);
    return ResponseResult.SUCCESS;
  }

  /**
   * 根据id查询分类
   *
   * @param id
   * @return
   */
  @Override
  public ResponseResult<CategoryVo> selectCategoryByIdSys(Long id) {
    Category category = getById(id);
    CategoryVo categoryVo = BeanUtil.copyBean(category, CategoryVo.class);
    return ResponseResult.okResult(categoryVo);
  }
}
