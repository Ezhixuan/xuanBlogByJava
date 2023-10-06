package com.ezhixuan.xuan_framework.domain.dto.article;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

  private Long id;
  // 标题
  @NotBlank(message = "标题不能为空")
  private String title;
  // 文章内容
  @NotBlank(message = "文章内容不能为空")
  private String content;
  // 文章摘要
  @NotBlank(message = "文章摘要不能为空")
  private String summary;
  // 所属分类id
  @NotNull(message = "所属分类不能为空")
  private Long categoryId;
  // 缩略图
  private String thumbnail;
  // 是否置顶（0否，1是）
  private String isTop;
  // 状态（0已发布，1草稿）
  @NotBlank(message = "状态不能为空")
  private String status;
  // 访问量
  private Long viewCount;
  // 是否允许评论 1是，0否
  @NotBlank(message = "是否允许评论不能为空")
  private String isComment;

  private List<Long> tags;
}
