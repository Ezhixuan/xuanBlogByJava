package com.ezhixuan.xuan_framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezhixuan.xuan_framework.domain.dto.comment.CommentPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Comment;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-09-28 10:18:59
 */
public interface CommentService extends IService<Comment> {

    /**
     * 评论列表
     * @param commentPageDTO
     * @return
     */
    ResponseResult commentList(CommentPageDTO commentPageDTO);
}
