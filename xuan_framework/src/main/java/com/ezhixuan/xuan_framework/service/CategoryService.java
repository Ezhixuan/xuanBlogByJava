package com.ezhixuan.xuan_framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezhixuan.xuan_framework.domain.dto.category.CategoryDTO;
import com.ezhixuan.xuan_framework.domain.dto.category.CategoryPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Category;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.category.CategoryVo;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
    ResponseResult<List<CategoryVo>> selectCategoryList();

    /**
     * 后台 展示所有发布的分类
     * @return
     */
    ResponseResult<List<CategoryVo>> selectCategoryListSys();

    /**
     * 导出excel
     * @param response
     */
    void exportExcelSys(HttpServletResponse response);

    /**
     * 分类列表
     *
     * @param categoryDTO
     * @return
     */
    ResponseResult<PageVo> selectCategoryPageSys(CategoryPageDTO categoryDTO);

    /**
     * 删除分类
     * @param ids
     * @return
     */
    ResponseResult<String> deleteCategoryByIdSys(List<Long> ids);

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    ResponseResult<String> insertCategorySys(CategoryDTO categoryDTO);

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    ResponseResult<String> updateCategoryByIdSys(CategoryDTO categoryDTO);

    /**
     * 根据id查询分类
     *
     * @param id
     * @return
     */
    ResponseResult<CategoryVo> selectCategoryByIdSys(Long id);
}
