package com.ezhixuan.xuan_framework.service.impl;

import static com.ezhixuan.xuan_framework.constant.CommonConstant.*;
import static com.ezhixuan.xuan_framework.constant.PageConstant.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.constant.RedisKeyConstant;
import com.ezhixuan.xuan_framework.dao.ArticleDao;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticleDTO;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticlePageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Article;
import com.ezhixuan.xuan_framework.domain.entity.ArticleTag;
import com.ezhixuan.xuan_framework.domain.entity.Category;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.article.ArticleDetailVo;
import com.ezhixuan.xuan_framework.domain.vo.article.ArticleListVo;
import com.ezhixuan.xuan_framework.domain.vo.article.ArticleSysVo;
import com.ezhixuan.xuan_framework.domain.vo.article.HotArticleVo;
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.exception.NullParaException;
import com.ezhixuan.xuan_framework.service.ArticleService;
import com.ezhixuan.xuan_framework.service.ArticleTagService;
import com.ezhixuan.xuan_framework.service.CategoryService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import com.ezhixuan.xuan_framework.utils.RedisUtil;
import com.ezhixuan.xuan_framework.utils.UserUtils;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
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

  @Resource private RedisUtil redisUtil;

  @Resource private ArticleTagService articleTagService;

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
   * @param articlePageDTO 分页dto
   * @return
   */
  @Override
  public PageVo articlePageQuery(ArticlePageDTO articlePageDTO) {
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
      // 为每个article查询赋予CategoryName
      Article oneArticle = records.get(fastIndex);
      oneArticle = queryCategoryNameByCategoryId(oneArticle, Article.class);
      records.set(fastIndex, oneArticle);
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
    PageVo pageVo = new PageVo(articleListVos, articlePage.getTotal());
    return pageVo;
  }

  /**
   * 根据id查询文章详情
   *
   * @param id 文章id
   * @return
   */
  @Override
  public ResponseResult<ArticleDetailVo> queryById(Long id) {
    // 要求在文章列表点击阅读全文时能够跳转到文章详情页面，可以让用户阅读文章正文。
    // 要求：①要在文章详情中展示其分类名
    // 1. 校验参数
    if (id == null) {
      throw new NullParaException(AppHttpCodeEnum.DATA_NOT_EXIST.getCode(), "参数不能为空");
    }
    // 2. 执行查询
    Article article = getById(id);
    // 3. viewCount实际上在redis中
    Long viewCount =
        redisUtil.getHash(RedisKeyConstant.ARTICLE_VIEW_COUNT_KEY, id.toString(), Long.class);
    article.setViewCount(viewCount);
    // 4. 封装
    ArticleDetailVo articleDetailVo = queryCategoryNameByCategoryId(article, ArticleDetailVo.class);
    // 5. 返回
    return ResponseResult.okResult(articleDetailVo);
  }

  /**
   * 查询CategoryName并赋值给Article
   *
   * @param article 源对象
   * @param clazz 目标对象类型
   * @return 目标对象
   * @param <T>
   */
  public <T> T queryCategoryNameByCategoryId(Article article, Class<T> clazz) {
    Long categoryId = article.getCategoryId();
    Category category = categoryService.getById(categoryId);
    article.setCategoryName(category.getName());
    T t = BeanUtil.copyBean(article, clazz);
    return t;
  }

  /**
   * 浏览量统计
   *
   * @param id
   * @return
   */
  @Override
  public ResponseResult<String> updateViewCount(Long id) {
    // 由于网站的浏览量修改相对比较频繁这里采用将浏览量的统计
    // 与回显统一存储到redis中，并设置定时器每5分钟定时推送一次浏览量数据给数据库
    // 1. 校验参数
    if (ObjectUtils.isEmpty(id)) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 查询redis缓存中是否包含所需数据,如果包含则添加,如果不包含则查询数据库
    String key = RedisKeyConstant.ARTICLE_VIEW_COUNT_KEY;
    Long count = redisUtil.getHash(key, id.toString(), Long.class);
    if (count == null) {
      Article article = getById(id);
      count = article.getViewCount();
      redisUtil.setHash(key, id.toString(), count);
    }
    // 3. 访问量加一
    redisUtil.incrHash(key, id.toString());
    // 4. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 新增文章
   *
   * @param articleDTO
   * @return
   */
  @Override
  @Transactional
  public ResponseResult<String> add(ArticleDTO articleDTO) {
    // 1. 封装
    Article article = BeanUtil.copyBean(articleDTO, Article.class);
    // 2. 执行新增
    save(article);
    // 3. 新增关系表
    List<ArticleTag> articleTags =
        articleDTO.getTags().stream()
            .map(
                tagId -> {
                  ArticleTag articleTag = new ArticleTag(article.getId(), tagId);
                  return articleTag;
                })
            .collect(Collectors.toList());
    // 4. 保存关系
    articleTagService.saveBatch(articleTags);
    // 5. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 后台查询文章列表
   *
   * @param articlePageDTO
   * @return
   */
  @Override
  public ResponseResult<PageVo> sysList(ArticlePageDTO articlePageDTO) {
    // 1. 校验参数
    articlePageDTO.check();
    // 2. 判断当前登录用户是否是管理员
    LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    if (!UserUtils.isSystem()) {
      // 2.1 不是管理员只能查看到自己的文章
      articleLambdaQueryWrapper.eq(Article::getCreateBy, UserUtils.getUser().getId());
    }
    // 2.2 如果标题不为空，查询标题
    articleLambdaQueryWrapper.like(
        StringUtils.hasText(articlePageDTO.getTitle()),
        Article::getTitle,
        articlePageDTO.getTitle());
    // 2.3 如果文章摘要不为空，查询文章摘要
    articleLambdaQueryWrapper.like(
        StringUtils.hasText(articlePageDTO.getSummary()),
        Article::getSummary,
        articlePageDTO.getSummary());
    // 2.4 根据创建时间排序
    articleLambdaQueryWrapper.orderByDesc(Article::getCreateTime);
    // 3. 分页
    Page<Article> articlePage =
        new Page<>(articlePageDTO.getPageNum(), articlePageDTO.getPageSize());
    page(articlePage, articleLambdaQueryWrapper);
    // 4. 封装返回
    List<ArticleSysVo> articleSysVos =
        BeanUtil.copyBeanList(articlePage.getRecords(), ArticleSysVo.class);
    PageVo pageVo = new PageVo(articleSysVos, articlePage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 后台查询文章
   *
   * @param id
   * @return
   */
  @Override
  public ResponseResult<Article> querySysById(Long id) {
    // 1. 校验参数
    if (id == null) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 查询文章信息
    Article article = getById(id);
    // 3. 查询文章tag信息
    List<ArticleTag> list =
        articleTagService.list(
            Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleId, article.getId()));
    List<Long> tags = list.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
    // 4. 封装返回
    ArticleSysVo articleSysVo = BeanUtil.copyBean(article, ArticleSysVo.class);
    articleSysVo.setTags(tags);
    return ResponseResult.okResult(articleSysVo);
  }

  /**
   * 后台修改文章
   *
   * @param articleDTO
   * @return
   */
  @Override
  @Transactional
  public ResponseResult<String> update(ArticleDTO articleDTO) {
    // 1. 校验参数
    if (articleDTO == null || articleDTO.getId() == null) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 如果tag有修改，需要更新articleTag表
    if (!ObjectUtils.isEmpty(articleDTO.getTags())) {
      // 2.1 删除原有关系
      articleTagService.remove(
          Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleId, articleDTO.getId()));
      // 2.2 建立新关系
      List<ArticleTag> articleTags =
          articleDTO.getTags().stream()
              .map(tag -> new ArticleTag(articleDTO.getId(), tag))
              .collect(Collectors.toList());
      articleTagService.saveBatch(articleTags);
    }
    // 3. 封装更新
    Article article = BeanUtil.copyBean(articleDTO, Article.class);
    updateById(article);
    // 4. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 后台删除文章
   *
   * @param ids
   * @return
   */
  @Override
  @Transactional
  public ResponseResult<String> delete(List<Long> ids) {
    // 1. 校验参数
    if (ObjectUtils.isEmpty(ids)) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 删除对应关系
    ids.forEach(
        id ->
            articleTagService.remove(
                Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleId, id)));
    // 3. 删除文章
    removeByIds(ids);
    // 4. 返回
    return ResponseResult.SUCCESS;
  }
}
