package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.domain.dto.article.ArticleDTO;
import com.ezhixuan.xuan_framework.domain.dto.article.ArticlePageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Article;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
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

  @ApiOperation("新增文章")
  @PostMapping
  public ResponseResult<String> add(@RequestBody ArticleDTO articleDTO) {
    return articleService.insertArticleSys(articleDTO);
  }

  @ApiOperation("文章分页查询")
  @GetMapping("list")
  public ResponseResult<PageVo> articleList(ArticlePageDTO articlePageDTO) {
    return articleService.selectArticlePageSys(articlePageDTO);
  }

  @GetMapping("/{id}")
  @ApiOperation("根据id查询文章详情")
  public ResponseResult<Article> queryById(@PathVariable Long id) {
    return articleService.selectArticleSys(id);
  }

  @PutMapping()
  @ApiOperation("修改文章")
  public ResponseResult<String> update(@RequestBody ArticleDTO articleDTO) {
    return articleService.updateArticleSys(articleDTO);
  }
  
  @DeleteMapping("/{id}")
    @ApiOperation("删除文章")
    public ResponseResult<String> delete(@PathVariable("id") List<Long> ids) {
        return articleService.deleteArticleSys(ids);
    }
}
