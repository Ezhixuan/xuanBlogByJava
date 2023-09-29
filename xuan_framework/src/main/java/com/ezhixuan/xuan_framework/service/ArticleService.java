package com.ezhixuan.xuan_framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticlePageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Article;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.article.ArticleDetailVo;
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
    List<HotArticleVo> hotArticleList();

    /**
     * 文章分页查询
     * @param articlePageDTO
     * @return
     */
    PageVo articlePageQuery(ArticlePageDTO articlePageDTO);

    /**
     * 根据id查询文章详情
     * @param id
     * @return
     */
    ResponseResult<ArticleDetailVo> queryById(Long id);
}
