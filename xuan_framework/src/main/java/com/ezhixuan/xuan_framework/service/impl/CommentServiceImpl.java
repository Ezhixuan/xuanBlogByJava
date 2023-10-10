package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.dao.CommentDao;
import com.ezhixuan.xuan_framework.dao.UserDao;
import com.ezhixuan.xuan_framework.domain.dto.comment.CommentDTO;
import com.ezhixuan.xuan_framework.domain.dto.comment.CommentPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Comment;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.comment.CommentVo;
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.service.CommentService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-09-28 10:18:59
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

  @Resource private UserDao userDao;

  /**
   * 评论列表
   *
   * @param commentPageDTO 分页参数
   * @return 评论列表
   */
  @Override
  public ResponseResult<PageVo> selectCommentPage(Integer type, CommentPageDTO commentPageDTO) {
    commentPageDTO.check();
    if (CommonConstant.ARTICLE_COMMENT.equals(type) && commentPageDTO.getArticleId() == null) {
      // 文章评论时文章id不能为空
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST.getCode(), "文章id不能为空");
    }
    // 构建查询条件
    LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
    commentLambdaQueryWrapper.eq(Comment::getRootId, CommonConstant.ROOT_COMMENT);
    commentLambdaQueryWrapper.eq(Comment::getType, type);
    commentLambdaQueryWrapper.eq(
            CommonConstant.ARTICLE_COMMENT.equals(type),
            Comment::getArticleId,
            commentPageDTO.getArticleId());
    commentLambdaQueryWrapper.orderByDesc(Comment::getCreateTime);
    // 构建分页查询
    Page<Comment> commentPage =
        new Page<>(commentPageDTO.getPageNum(), commentPageDTO.getPageSize());
    page(commentPage, commentLambdaQueryWrapper);
    // 查询根评论下的子评论
    List<CommentVo> commentVos = selectUserNameByComments(commentPage.getRecords());
    commentVos.forEach(
        commentVo -> {
          List<Comment> childrenComment =
              list(
                  Wrappers.<Comment>lambdaQuery()
                      .eq(Comment::getRootId, commentVo.getId())
                      .orderByAsc(Comment::getCreateTime));
          List<CommentVo> childrenCommentVo = selectUserNameByComments(childrenComment);
          commentVo.setChildren(childrenCommentVo);
        });
    // 封装返回
    PageVo pageVo = new PageVo(commentVos, commentPage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 查询评论者的用户名和被评论的用户的用户名并且转换为List<CommentVo>的类型
   *
   * @param comments 评论列表
   * @return 评论vo列表
   */
  public List<CommentVo> selectUserNameByComments(List<Comment> comments) {
    // 转换
    List<CommentVo> commentVos = BeanUtil.copyBeanList(comments, CommentVo.class);
    commentVos.forEach(
        commentVo -> {
          String userName = userDao.selectUserName(commentVo.getCreateBy());
          commentVo.setUsername(userName);
          // 根评论是没有被评论用户的
          if (!CommonConstant.ROOT_COMMENT.equals(commentVo.getToCommentUserId())) {
            String commentedName = userDao.selectUserName(commentVo.getToCommentUserId());
            commentVo.setToCommentUserName(commentedName);
          }
        });
    return commentVos;
  }

  /**
   * 添加评论
   *
   * @param commentDTO 评论dto
   * @return 添加结果
   */
  @Override
  public ResponseResult<String> insertComment(CommentDTO commentDTO) {
    // 保存
    Comment comment = BeanUtil.copyBean(commentDTO, Comment.class);
    save(comment);
    return ResponseResult.SUCCESS;
  }
}
