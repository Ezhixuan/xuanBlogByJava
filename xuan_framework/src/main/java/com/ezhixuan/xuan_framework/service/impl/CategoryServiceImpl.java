package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.dao.CategoryDao;
import com.ezhixuan.xuan_framework.domain.entity.Category;
import com.ezhixuan.xuan_framework.domain.vo.category.CategoryListVo;
import com.ezhixuan.xuan_framework.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

  /**
   * 分类列表 只展示有正式发布文章的分类
   *
   * @return
   */
  @Override
  public List<CategoryListVo> getCategoryList() {
    // 1. 确定查询条件 需要有正式发布的文章
    List<CategoryListVo> categoryList = categoryDao.getCategoryList();
    log.info("category = {}",categoryList);
    return categoryList;
  }
}
