package com.ezhixuan.xuan_blog.controller;

import com.ezhixuan.xuan_framework.domain.dto.comment.CommentDTO;
import com.ezhixuan.xuan_framework.domain.dto.comment.CommentPageDTO;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: xuanBlog
 * @description: 评论控制器
 * @author: Mr.Xuan
 * @create: 2023-09-28 10:20
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论控制器")
public class CommentController {

  @Autowired private CommentService commentService;

  @GetMapping("/commentList")
  @ApiOperation("评论列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true),
    @ApiImplicitParam(name = "pageNum", value = "页码", required = true),
    @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true)
  })
  public ResponseResult<PageVo> commentList(CommentPageDTO commentPageDTO) {
    return commentService.commentList(commentPageDTO);
  }

  @PostMapping
  @ApiOperation("添加评论")
  public ResponseResult<String> addComment(@RequestBody CommentDTO commentDTO){
    return commentService.addComment(commentDTO);
  }
}
