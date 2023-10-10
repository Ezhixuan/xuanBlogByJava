package com.ezhixuan.xuan_blog.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticlePageDTO;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.article.ArticleVo;
import com.ezhixuan.xuan_framework.domain.vo.article.HotArticleVo;
import com.ezhixuan.xuan_framework.handler.validated.Select;
import com.ezhixuan.xuan_framework.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @program: xuanBlog
 * @description: 文章控制器
 * @author: Mr.Xuan
 * @create: 2023-09-23 17:35
 */
@Api(tags = "文章控制器")
@RestController
@RequestMapping("/article")
@Slf4j
@Validated
public class ArticleController {
  @Resource private ArticleService articleService;

  @Log(businessName = "热门文章列表")
  @ApiOperation("热门文章列表")
  @GetMapping("/hotArticleList")
  public ResponseResult<List<HotArticleVo>> selectHotArticlePage() {
    return articleService.selectHotArticlePage();
  }

  @Log(businessName = "分页查询文章列表")
  @ApiOperation("分页查询文章列表")
  @GetMapping("articleList")
  public ResponseResult<PageVo> selectArticlePage(ArticlePageDTO articlePageDTO) {
    return articleService.selectArticlePage(articlePageDTO);
  }

  @Log(businessName = "文章详情")
  @ApiOperation("文章详情")
  @GetMapping("/{id}")
  public ResponseResult<ArticleVo> selectArticleById(
      @PathVariable("id") @NotNull(message = "ArticleId不能为空", groups = Select.class) Long id) {
    return articleService.selectArticleById(id);
  }

  @Log(businessName = "浏览量统计")
  @ApiOperation("浏览量统计")
  @PutMapping("/updateViewCount/{id}")
  public ResponseResult<String> updateViewCount(
      @PathVariable("id") @NotNull(message = "ArticleId不能为空", groups = Select.class) Long id) {
    return articleService.updateViewCountById(id);
  }
}
