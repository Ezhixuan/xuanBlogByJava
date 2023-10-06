package com.ezhixuan.xuan_framework.service.impl;

import static com.ezhixuan.xuan_framework.constant.CommonConstant.*;
import static com.ezhixuan.xuan_framework.constant.PageConstant.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.RedisKeyConstant;
import com.ezhixuan.xuan_framework.dao.ArticleDao;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticleDTO;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticlePageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Article;
import com.ezhixuan.xuan_framework.domain.entity.ArticleTag;
import com.ezhixuan.xuan_framework.domain.entity.Category;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.article.ArticleListVo;
import com.ezhixuan.xuan_framework.domain.vo.article.ArticleSysVo;
import com.ezhixuan.xuan_framework.domain.vo.article.ArticleVo;
import com.ezhixuan.xuan_framework.domain.vo.article.HotArticleVo;
import com.ezhixuan.xuan_framework.service.ArticleService;
import com.ezhixuan.xuan_framework.service.ArticleTagService;
import com.ezhixuan.xuan_framework.service.CategoryService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import com.ezhixuan.xuan_framework.utils.RedisUtil;
import com.ezhixuan.xuan_framework.utils.UserUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-09-23 17:32:59
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

  @Resource private CategoryService categoryService;
  @Resource private ArticleTagService articleTagService;
  @Resource private RedisUtil redisUtil;

  /**
   * 热门文章列表 虽然根据viewCount计算，更新相对频繁，但是总的viewCount是每10分钟更新一次，并且该接口访问频率较高，适合缓存
   *
   * @return 热点文章
   */
  @Override
  public ResponseResult<List<HotArticleVo>> selectHotArticlePage() {
    List<HotArticleVo> valueList =
        redisUtil.getValueList(RedisKeyConstant.CACHE_HOT_ARTICLE, HotArticleVo.class);
    if (!ObjectUtils.isEmpty(valueList)) {
      return ResponseResult.okResult(valueList);
    }
    // 构建查询条件
    LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    articleLambdaQueryWrapper.eq(Article::getStatus, STATUS_NORMAL);
    articleLambdaQueryWrapper.orderByDesc(Article::getViewCount);
    // 分页
    Page<Article> articlePage = new Page<>(FIRST_OF_PAGE, TEN_OF_SIZE);
    page(articlePage, articleLambdaQueryWrapper);
    List<HotArticleVo> hotArticleVos =
        BeanUtil.copyBeanList(articlePage.getRecords(), HotArticleVo.class);
    // 缓存
    redisUtil.setValue(RedisKeyConstant.CACHE_HOT_ARTICLE, hotArticleVos);
    return ResponseResult.okResult(hotArticleVos);
  }

  /**
   * 文章分页查询
   *
   * @param articlePageDTO 分页dto
   * @return 分页文章
   */
  @Override
  public ResponseResult<PageVo> selectArticlePage(ArticlePageDTO articlePageDTO) {
    // 检查参数
    articlePageDTO.check();
    // 构造查询条件
    LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    articleLambdaQueryWrapper.eq(Article::getStatus, ARTICLE_PUBLISHED_DRAFT);
    articleLambdaQueryWrapper.orderByDesc(Article::getIsTop, Article::getCreateTime);
    articleLambdaQueryWrapper.eq(
        ObjectUtils.isEmpty(articlePageDTO.getCategoryId()) && articlePageDTO.getCategoryId() > 0,
        Article::getCategoryId,
        articlePageDTO.getCategoryId());
    // 分页
    Page<Article> articlePage =
        new Page<>(articlePageDTO.getPageNum(), articlePageDTO.getPageSize());
    page(articlePage, articleLambdaQueryWrapper);
    // 封装返回
    List<ArticleListVo> articleListVos =
        BeanUtil.copyBeanList(articlePage.getRecords(), ArticleListVo.class);
    PageVo pageVo = new PageVo(articleListVos, articlePage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 根据id查询文章详情
   *
   * @param id 文章id
   * @return 文章详情
   */
  @Override
  public ResponseResult<ArticleVo> selectArticleById(
      @NotNull(message = "查询ArticleId不能为空") Long id) {
    String key = RedisKeyConstant.CACHE_ARTICLE_BY_ID + id;
    ArticleVo value = redisUtil.getValue(key, ArticleVo.class);
    if (!ObjectUtils.isEmpty(value)) {
      return ResponseResult.okResult(value);
    }
    Article article = getById(id);
    article.setViewCount(article.getViewCount() + 1);
    // 封装返回
    ArticleVo articleVo = selectCategoryNameBack(article, ArticleVo.class);
    redisUtil.setValue(key, articleVo);
    return ResponseResult.okResult(articleVo);
  }

  /**
   * 查询CategoryName并赋值给Article
   *
   * @param article 源对象
   * @param clazz 目标对象类型
   * @return 目标对象
   * @param <T> 目标对象
   */
  public <T> T selectCategoryNameBack(Article article, Class<T> clazz) {
    Long categoryId = article.getCategoryId();
    Category category = categoryService.getById(categoryId);
    article.setCategoryName(category.getName());
    return BeanUtil.copyBean(article, clazz);
  }

  /**
   * 浏览量统计
   *
   * @param id 文章id
   * @return 处理结果
   */
  @Override
  @Transactional
  public ResponseResult<String> updateViewCount(@NotNull(message = "ArticleId不能为空") Long id) {
    // 查询redis缓存中是否包含所需数据,如果包含则添加,如果不包含则查询数据库
    String key = RedisKeyConstant.ARTICLE_VIEW_COUNT_KEY;
    Long count = redisUtil.getHash(key, id.toString(), Long.class);
    if (ObjectUtils.isEmpty(count)) {
      Article article = getById(id);
      count = article.getViewCount();
      redisUtil.setHash(key, id.toString(), count);
    }
    redisUtil.incrHash(key, id.toString());
    // 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 后台新增文章
   *
   * @param articleDTO 文章dto
   * @return 处理结果
   */
  @Override
  @Transactional
  public ResponseResult<String> insertArticleSys(ArticleDTO articleDTO) {
    Article article = BeanUtil.copyBean(articleDTO, Article.class);
    save(article);
    // 新增关系表
    List<ArticleTag> articleTags =
        articleDTO.getTags().stream()
            .map(tagId -> new ArticleTag(article.getId(), tagId))
            .collect(Collectors.toList());
    articleTagService.saveBatch(articleTags);
    // 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 后台查询文章列表
   *
   * @param articlePageDTO 分页Dto
   * @return 分页文章
   */
  @Override
  public ResponseResult<PageVo> selectArticlePageSys(ArticlePageDTO articlePageDTO) {
    // 校验参数
    articlePageDTO.check();
    // 判断当前登录用户是否是管理员
    LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    if (!UserUtils.isSystem()) {
      // 不是管理员只能查看到自己的文章
      articleLambdaQueryWrapper.eq(Article::getCreateBy, UserUtils.getUser().getId());
    }
    articleLambdaQueryWrapper.like(
        StringUtils.hasText(articlePageDTO.getTitle()),
        Article::getTitle,
        articlePageDTO.getTitle());
    articleLambdaQueryWrapper.like(
        StringUtils.hasText(articlePageDTO.getSummary()),
        Article::getSummary,
        articlePageDTO.getSummary());
    articleLambdaQueryWrapper.orderByDesc(Article::getCreateTime);
    // 分页
    Page<Article> articlePage =
        new Page<>(articlePageDTO.getPageNum(), articlePageDTO.getPageSize());
    page(articlePage, articleLambdaQueryWrapper);
    // 封装返回
    List<ArticleSysVo> articleSysVos =
        BeanUtil.copyBeanList(articlePage.getRecords(), ArticleSysVo.class);
    PageVo pageVo = new PageVo(articleSysVos, articlePage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 后台查询文章
   *
   * @param id 文章id
   * @return 文章信息
   */
  @Override
  public ResponseResult<Article> selectArticleSys(@NotNull(message = "文章id不能为空") Long id) {
    Article article = getById(id);
    List<ArticleTag> list =
        articleTagService.list(
            Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleId, article.getId()));
    List<Long> tags = list.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
    // 封装返回
    ArticleSysVo articleSysVo = BeanUtil.copyBean(article, ArticleSysVo.class);
    articleSysVo.setTags(tags);
    return ResponseResult.okResult(articleSysVo);
  }

  /**
   * 后台修改文章
   *
   * @param articleDTO 文章信息
   * @return 处理结果
   */
  @Override
  @Transactional
  public ResponseResult<String> updateArticleSys(ArticleDTO articleDTO) {
    // 如果tag有修改，需要更新articleTag表
    if (!ObjectUtils.isEmpty(articleDTO.getTags())) {
      // 删除原有关系
      articleTagService.remove(
          Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleId, articleDTO.getId()));
      List<ArticleTag> articleTags =
          articleDTO.getTags().stream()
              .map(tag -> new ArticleTag(articleDTO.getId(), tag))
              .collect(Collectors.toList());
      articleTagService.saveBatch(articleTags);
    }
    // 封装更新
    Article article = BeanUtil.copyBean(articleDTO, Article.class);
    updateById(article);
    // 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 后台删除文章
   *
   * @param ids 文章id
   * @return 处理结果
   */
  @Override
  @Transactional
  public ResponseResult<String> deleteArticleSys(@NotEmpty(message = "请选择要删除的文章") List<Long> ids) {
    // 删除对应关系
    ids.forEach(
        id ->
            articleTagService.remove(
                Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleId, id)));
    removeByIds(ids);
    // 返回
    return ResponseResult.SUCCESS;
  }
}
