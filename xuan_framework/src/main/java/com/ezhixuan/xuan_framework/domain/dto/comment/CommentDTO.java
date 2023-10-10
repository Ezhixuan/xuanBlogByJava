package com.ezhixuan.xuan_framework.domain.dto.comment;

import com.ezhixuan.xuan_framework.handler.validated.Insert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
  @NotNull(message = "被评论文章不存在", groups = Insert.class)
  private Long articleId;

  @ApiModelProperty("根评论id")
  @NotNull(message = "根评论id不能为空", groups = Insert.class)
  private Long rootId;

  @ApiModelProperty("评论类型（0代表文章评论，1代表友链评论）")
  @NotBlank(message = "评论类型不能为空", groups = Insert.class)
  private String type;

  @ApiModelProperty("评论内容")
  @NotBlank(message = "评论内容不能为空", groups = Insert.class)
  private String content;

  @ApiModelProperty("被回复的用户的id")
  private Long toCommentUserId;

  @ApiModelProperty("被回复的评论id")
  private Long toCommentId;
}
