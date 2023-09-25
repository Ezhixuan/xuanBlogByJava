package com.ezhixuan.xuan_framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezhixuan.xuan_framework.domain.entity.Category;
import com.ezhixuan.xuan_framework.domain.vo.category.CategoryListVo;

import java.util.List;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-09-24 17:36:29
 */
public interface CategoryService extends IService<Category> {

    /**
     * 分类列表
     * 只展示有正式发布文章的分类
     *
     * @return
     */
    List<CategoryListVo> getCategoryList();

}
