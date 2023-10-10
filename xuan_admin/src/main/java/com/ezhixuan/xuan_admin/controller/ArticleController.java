package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticleDTO;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticlePageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Article;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Select;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import com.ezhixuan.xuan_framework.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @program: xuanBlog
 * @description: 标签控制器
 * @author: Mr.Xuan
 * @create: 2023-10-1 17:01
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

  @Resource private ArticleService articleService;

  @Log(businessName = "新增文章")
  @ApiOperation("新增文章")
  @PostMapping
  public ResponseResult<String> insertArticleSys(
      @RequestBody @Validated(Insert.class) ArticleDTO articleDTO) {
    return articleService.insertArticleSys(articleDTO);
  }

  @Log(businessName = "文章分页查询")
  @ApiOperation("文章分页查询")
  @GetMapping("list")
  public ResponseResult<PageVo> selectArticlePageSys(ArticlePageDTO articlePageDTO) {
    return articleService.selectArticlePageSys(articlePageDTO);
  }

  @Log(businessName = "根据id查询文章详情")
  @GetMapping("/{id}")
  @ApiOperation("根据id查询文章详情")
  public ResponseResult<Article> selectArticleSys(
      @PathVariable @Validated @NotNull(message = "文章id不能为空", groups = Select.class) Long id) {
    return articleService.selectArticleByIdSys(id);
  }

  @Log(businessName = "修改文章")
  @PutMapping
  @ApiOperation("修改文章")
  public ResponseResult<String> updateArticleSys(
      @RequestBody @Validated(Update.class) ArticleDTO articleDTO) {
    return articleService.updateArticleSys(articleDTO);
  }

  @Log(businessName = "删除文章")
  @DeleteMapping("/{id}")
  @ApiOperation("删除文章")
  public ResponseResult<String> deleteArticleSys(
      @PathVariable("id") @Validated @NotEmpty(message = "请选择要删除的文章", groups = Insert.class)
          List<Long> ids) {
    return articleService.deleteArticleByIdSys(ids);
  }
}
