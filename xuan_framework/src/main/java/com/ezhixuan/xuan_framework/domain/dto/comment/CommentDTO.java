package com.ezhixuan.xuan_framework.domain.dto.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 评论DTO
 * @author: Mr.Xuan
 * @create: 2023-09-28 11:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评论DTO")
public class CommentDTO {

  @ApiModelProperty("文章id")
  private Long articleId;

  @ApiModelProperty("根评论id")
  private Long rootId;

  @ApiModelProperty("评论类型（0代表文章评论，1代表友链评论）")
  private String type;

  @ApiModelProperty("评论内容")
  private String content;

  @ApiModelProperty("所回复的目标评论的userid")
  private Long toCommentUserId;

  @ApiModelProperty("回复目标评论id")
  private Long toCommentId;
}
