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

  @ApiModelProperty("所回复的目标评论的userid")
  private Long toCommentUserId;

  @ApiModelProperty("所回复的目标评论的用户名")
  private String toCommentUserName;

  @ApiModelProperty("回复目标评论id")
  private Long toCommentId;

  @ApiModelProperty("回复目标评论内容")
  private Long createBy;

  @ApiModelProperty("创建时间")
  private Date createTime;

  @ApiModelProperty("用户名")
  private String username;

  @ApiModelProperty("子评论")
  private List<CommentVo> children;
}
