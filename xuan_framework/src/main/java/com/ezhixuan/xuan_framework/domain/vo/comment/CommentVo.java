package com.ezhixuan.xuan_framework.domain.vo.comment;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
  @ApiModelProperty("评论id")
  private Long id;

  @ApiModelProperty("文章id")
  private Long articleId;

  @ApiModelProperty("根评论id")
  private Long rootId;

  @ApiModelProperty("评论内容")
  private String content;

  @ApiModelProperty("被回复的用户id")
  private Long toCommentUserId;

  @ApiModelProperty("被回复用户的用户名")
  private String toCommentUserName;

  @ApiModelProperty("被回复的评论id")
  private Long toCommentId;

  @ApiModelProperty("评论创建人")
  private Long createBy;

  @ApiModelProperty("创建时间")
  private Date createTime;

  @ApiModelProperty("用户名")
  private String username;

  @ApiModelProperty("子评论")
  private List<CommentVo> children;
}
