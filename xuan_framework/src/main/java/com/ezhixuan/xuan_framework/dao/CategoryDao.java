package com.ezhixuan.xuan_framework.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezhixuan.xuan_framework.domain.entity.Category;
import com.ezhixuan.xuan_framework.domain.vo.category.CategoryVo;

import java.util.List;

/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-24 17:36:29
 */
public interface CategoryDao extends BaseMapper<Category> {

    /**
     * 查询有正式发布的文章的列表
     * @return
     */
    List<CategoryVo> getCategoryList();
}
