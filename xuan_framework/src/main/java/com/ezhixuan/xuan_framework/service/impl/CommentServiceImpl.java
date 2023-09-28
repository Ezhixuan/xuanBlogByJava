package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.dao.CommentDao;
import com.ezhixuan.xuan_framework.domain.dto.comment.CommentPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Comment;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.comment.CommentVo;
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.service.CommentService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-09-28 10:18:59
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

  /**
   * 评论列表
   *
   * @param commentPageDTO
   * @return
   */
  @Override
  public ResponseResult commentList(CommentPageDTO commentPageDTO) {
    // 1. 校验参数
    commentPageDTO.check();
    if (commentPageDTO.getArticleId() == null) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST.getCode(), "文章id不能为空");
    }
    // 2. 构建查询条件
    // 查询改文章的根评论
    LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
    // 2.1 根据文章id查询
    commentLambdaQueryWrapper.eq(Comment::getArticleId, commentPageDTO.getArticleId());
    // 2.2 查询文章根评论
    commentLambdaQueryWrapper.eq(Comment::getRootId, CommonConstant.ARTICLE_ROOT_COMMENT);
    // 2.3 根据发布时间倒序排序
    commentLambdaQueryWrapper.orderByDesc(Comment::getCreateTime);
    // 3. 构建分页查询
    Page<Comment> commentPage =
        new Page<>(commentPageDTO.getPageNum(), commentPageDTO.getPageSize());
    page(commentPage, commentLambdaQueryWrapper);
    // 4. 查询根评论下的子评论
    List<CommentVo> commentVos = BeanUtil.copyBeanList(commentPage.getRecords(), CommentVo.class);
    commentVos.forEach(
        commentVo -> {
          // 4.1 查询子评论
          List<Comment> childrenComment =
              list(
                  Wrappers.<Comment>lambdaQuery()
                      .eq(Comment::getRootId, commentVo.getId())
                      .orderByAsc(Comment::getCreateTime));
          List<CommentVo> childrenCommentVo =
              BeanUtil.copyBeanList(childrenComment, CommentVo.class);
          commentVo.setChildren(childrenCommentVo);
        });
    // 5. 封装返回
    PageVo pageVo = new PageVo(commentVos, commentPage.getTotal());
    return ResponseResult.okResult(pageVo);
  }
}
