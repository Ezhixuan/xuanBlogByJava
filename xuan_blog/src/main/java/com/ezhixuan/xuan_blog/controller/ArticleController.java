package com.ezhixuan.xuan_blog.controller;

import com.ezhixuan.xuan_framework.domain.dto.article.ArticlePageDTO;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.article.HotArticleVo;
import com.ezhixuan.xuan_framework.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class ArticleController {

  @Resource private ArticleService articleService;

  @ApiOperation("热门文章列表")
  @GetMapping("/hotArticleList")
  public ResponseResult hotArticleList() {
    List<HotArticleVo> hotArticleVos = articleService.hotArticleList();
    return ResponseResult.okResult(hotArticleVos);
  }

  @ApiOperation("分页查询文章列表")
  @GetMapping("articleList")
  public ResponseResult articleList(ArticlePageDTO articlePageDTO){
    log.info("articlePageDTO = {}", articlePageDTO);
    PageVo page = articleService.articlePageQuery(articlePageDTO);
    return ResponseResult.okResult(page);
  }
}
