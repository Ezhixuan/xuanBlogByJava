package com.ezhixuan.xuan_framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticleDTO;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticlePageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Article;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.article.ArticleVo;
import com.ezhixuan.xuan_framework.domain.vo.article.HotArticleVo;
import java.util.List;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-09-23 17:32:59
 */
public interface ArticleService extends IService<Article> {

    /**
     * 热门文章列表
     *
     * @return
     */
    ResponseResult<List<HotArticleVo>> selectHotArticlePage();

    /**
     * 文章分页查询
     *
     * @param articlePageDTO
     * @return
     */
    ResponseResult<PageVo> selectArticlePage(ArticlePageDTO articlePageDTO);

    /**
     * 根据id查询文章详情
     * @param id
     * @return
     */
    ResponseResult<ArticleVo> selectArticleById(Long id);

    /**
     * 浏览量统计
     * @param id
     * @return
     */
    ResponseResult<String> updateViewCountById(Long id);

    /**
     * 新增文章
     * @param articleDTO
     * @return
     */
    ResponseResult<String> insertArticleSys(ArticleDTO articleDTO);

    /**
     * 后台查询文章列表
     *
     * @param articlePageDTO
     * @return
     */
    ResponseResult<PageVo> selectArticlePageSys(ArticlePageDTO articlePageDTO);

    /**
     * 后台查询文章
     * @param id
     * @return
     */
    ResponseResult<Article> selectArticleByIdSys(Long id);

    /**
     * 后台修改文章
     * @param articleDTO
     * @return
     */
    ResponseResult<String> updateArticleSys(ArticleDTO articleDTO);

    /**
     * 后台删除文章
     * @param ids
     * @return
     */
    ResponseResult<String> deleteArticleByIdSys(List<Long> ids);
}
