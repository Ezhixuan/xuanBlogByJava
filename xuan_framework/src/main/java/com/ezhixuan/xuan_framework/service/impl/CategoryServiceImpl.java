package com.ezhixuan.xuan_framework.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.dao.CategoryDao;
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
import org.springframework.util.ObjectUtils;
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

  @Resource private CategoryDao categoryDao;

  @Resource private ArticleService articleService;

  /**
   * 分类列表 只展示有正式发布文章的分类
   *
   * @return
   */
  @Override
  public List<CategoryVo> getCategoryList() {
    // 1. 确定查询条件 需要有正式发布的分类
    List<CategoryVo> categoryList = categoryDao.getCategoryList();
    log.info("category = {}", categoryList);
    return categoryList;
  }

  /**
   * 展示所有发布的分类
   *
   * @return
   */
  @Override
  public ResponseResult<List<CategoryVo>> listAllCategory() {
    // 1. 查询所有分类
    List<Category> list = list();
    // 2. 封装返回
    List<CategoryVo> categoryVos = BeanUtil.copyBeanList(list, CategoryVo.class);
    return ResponseResult.okResult(categoryVos);
  }

  /**
   * 导出excel
   *
   * @param response
   */
  @Override
  public void export(HttpServletResponse response) {
    try {
      // 1. 设置下载返回头
      WebUtils.setDownLoadHeader("分类.xlsx", response);
      // 2. 获取需要导出的数据
      List<Category> list = list();
      List<ExcelCategoryVo> excelCategoryVos = BeanUtil.copyBeanList(list, ExcelCategoryVo.class);
      // 3. 将数据写入excel中
      EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class)
          .autoCloseStream(Boolean.FALSE)
          .sheet("分类导出")
          .doWrite(excelCategoryVos);
    } catch (Exception e) {
      // 4. 如果出现异常
      // 4.1 重置response
      response.reset();
      ResponseResult<Object> result = ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
      String jsonStr = JSONUtil.toJsonStr(result);
      WebUtils.renderString(response, jsonStr);
    }
  }

  /**
   * 分类列表
   *
   * @param categoryDTO
   * @return
   */
  @Override
  public ResponseResult<PageVo> queryList(CategoryPageDTO categoryDTO) {
    // 1. 校验参数
    categoryDTO.check();
    // 2. 构建查询
    LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
    // 2.1 如果分类名存在,按照分类名模糊查询
    categoryLambdaQueryWrapper.like(
        StringUtils.hasText(categoryDTO.getName()), Category::getName, categoryDTO.getName());
    // 2.2 如果状态存在，按照状态查询，否则查询正常状态
    if (StringUtils.hasText(categoryDTO.getStatus())) {
      categoryLambdaQueryWrapper.eq(Category::getStatus, categoryDTO.getStatus());
    } else {
      categoryLambdaQueryWrapper.eq(Category::getStatus, CommonConstant.STATUS_NORMAL);
    }
    // 2.3 根据id排序
    categoryLambdaQueryWrapper.orderByAsc(Category::getId);
    // 3. 分页查询
    Page<Category> categoryPage = new Page<>(categoryDTO.getPageNum(), categoryDTO.getPageSize());
    page(categoryPage, categoryLambdaQueryWrapper);
    // 4. 封装返回
    PageVo pageVo = new PageVo(categoryPage.getRecords(), categoryPage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 删除分类
   *
   * @param ids
   * @return
   */
  @Override
  public ResponseResult<String> delete(List<Long> ids) {
    // 1. 校验参数
    if (ObjectUtils.isEmpty(ids)) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 查询该分类下是否有文章
    ids.forEach(
        id -> {
          int count =
              articleService.count(Wrappers.<Article>lambdaQuery().eq(Article::getCategoryId, id));
          if (count > 0) {
            throw new BaseException("该分类下有文章，不能删除");
          }
        });
    // 3. 删除分类
    removeByIds(ids);
    // 4. 返回
    return ResponseResult.SUCCESS;
  }
}
