package com.ezhixuan.xuan_framework.service.impl;

import static com.ezhixuan.xuan_framework.constant.CommonConstant.*;
import static com.ezhixuan.xuan_framework.constant.PageConstant.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.dao.ArticleDao;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticlePageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Article;
import com.ezhixuan.xuan_framework.domain.vo.PageResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.article.ArticleListVo;
import com.ezhixuan.xuan_framework.domain.vo.article.HotArticleVo;
import com.ezhixuan.xuan_framework.service.ArticleService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-09-23 17:32:59
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {
  /**
   * 热门文章列表
   *
   * @return
   */
  @Override
  public List<HotArticleVo> hotArticleList() {
    // 查询浏览量最高的前十篇文章的信息，要求展示文章标题和浏览量，把能让用户自己点击跳转到具体的文章详情进行浏览。
    // 注意： 不能把草稿展示出来，不能把删除了的文章查询出来，要求按照浏览量进行降序排序

    // 1. 构建查询条件
    LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    // 1.1 不查询草稿
    articleLambdaQueryWrapper.eq(Article::getStatus, ARTICLE_PUBLISHED_DRAFT);
    // 1.2 按浏览量查询
    articleLambdaQueryWrapper.orderByDesc(Article::getViewCount);
    // 2. 查询十条记录
    Page<Article> articlePage = new Page<>(FIRST_OF_PAGE, TEN_OF_SIZE);
    // 2.1 执行查询
    page(articlePage, articleLambdaQueryWrapper);
    // 3. 返回
    List<HotArticleVo> hotArticleVos =
        BeanUtil.copyBeanList(articlePage.getRecords(), HotArticleVo.class);
    return hotArticleVos;
  }

  /**
   * 文章分页查询
   *
   * @param articlePageDTO
   * @return
   */
  @Override
  public PageResponseResult articlePageQuery(ArticlePageDTO articlePageDTO) {
    // 在首页和分类页面都需要查询文章列表。
    // 首页：查询所有的文章
    // 分类页面：查询对应分类下的文章
    // 要求：①只能查询正式发布的文章 ②置顶的文章要显示在最前面
    // 1. 检查参数
    articlePageDTO.check();
    // 2. 构造查询条件
    Page<Article> articlePage =
        new Page<>(articlePageDTO.getPageNum(), articlePageDTO.getPageSize());
    LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    // 2.1 只能是正式发布的文章
    articleLambdaQueryWrapper.eq(Article::getStatus, ARTICLE_PUBLISHED_DRAFT);
    // 2.2 按发布时间排序
    articleLambdaQueryWrapper.orderByDesc(Article::getUpdateTime);
    // 2.3 如果有分类的话按照分类查询
    articleLambdaQueryWrapper.eq(
        Objects.nonNull(articlePageDTO.getCategoryId()) && articlePageDTO.getCategoryId() > 0,
        Article::getCategoryId,
        articlePageDTO.getCategoryId());
    // 3. 执行查询
    page(articlePage, articleLambdaQueryWrapper);
    // 4. 置顶文章显示在最前面 -- 这里使用快慢指针交换位置
    List<Article> records = articlePage.getRecords();
    int slowIndex = 0;
    for (int fastIndex = 0; fastIndex < records.size(); fastIndex++) {
      // 当且仅当fastIndex找到isTop文章时
      if (CommonConstant.ARTICLE_IS_TOP.equals(records.get(fastIndex).getIsTop())) {
        // 交换fastIndex 与 slowIndex 的文章位置
        Article article = records.get(slowIndex);
        records.set(slowIndex, records.get(fastIndex));
        records.set(fastIndex, article);
        // 交换完后slowIndex指针右移
        slowIndex++;
      }
    }
    // 5 封装
    List<ArticleListVo> articleListVos = BeanUtil.copyBeanList(records, ArticleListVo.class);
    // 5.1 返回
    PageResponseResult pageResponseResult =
        new PageResponseResult(
            articlePageDTO.getPageNum(),
            articlePageDTO.getPageSize(),
            (int) articlePage.getTotal());
    pageResponseResult.setData(articleListVos);
    return pageResponseResult;
  }
}