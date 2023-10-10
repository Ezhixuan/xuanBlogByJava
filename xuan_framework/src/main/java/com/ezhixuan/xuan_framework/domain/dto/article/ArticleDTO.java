package com.ezhixuan.xuan_framework.domain.dto.article;

import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ezhixuan @Description 文章DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("文章DTO")
public class ArticleDTO {

  @NotNull(message = "文章id不能为空", groups = Update.class)
  @ApiModelProperty("文章id")
  private Long id;

  @NotBlank(
      message = "标题不能为空",
      groups = {Insert.class, Update.class})
  @ApiModelProperty("文章标题")
  private String title;

  @NotBlank(
      message = "文章内容不能为空",
      groups = {Insert.class, Update.class})
  @ApiModelProperty("文章内容")
  private String content;

  @NotBlank(
      message = "文章摘要不能为空",
      groups = {Insert.class, Update.class})
  @ApiModelProperty("文章摘要")
  private String summary;

  @NotNull(
      message = "所属分类不能为空",
      groups = {Insert.class, Update.class})
  @ApiModelProperty("所属分类id")
  private Long categoryId;

  @ApiModelProperty("文章缩略图")
  private String thumbnail;

  @NotBlank(
      message = "是否置顶不能为空",
      groups = {Insert.class, Update.class})
  @ApiModelProperty("是否置顶")
  private String isTop;

  @NotBlank(
      message = "状态不能为空",
      groups = {Insert.class})
  @ApiModelProperty("状态")
  private String status;

  @ApiModelProperty("浏览量")
  private Long viewCount;

  @NotBlank(
      message = "是否允许评论不能为空",
      groups = {Insert.class})
  @ApiModelProperty("是否允许评论")
  private String isComment;

  @ApiModelProperty("标签id")
  private List<Long> tags;
}
