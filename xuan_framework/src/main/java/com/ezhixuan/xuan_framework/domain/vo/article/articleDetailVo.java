package com.ezhixuan.xuan_framework.domain.vo.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description:
 * @author: Mr.Xuan
 * @create: 2023-09-25 15:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("文章详情返回")
public class articleDetailVo {

  @ApiModelProperty("文章id")
  private Long id;

  @ApiModelProperty("标题")
  private String title;

  @ApiModelProperty("文章摘要")
  private String summary;

  @ApiModelProperty("分类id")
  private Long categoryId;

  @ApiModelProperty("文章内容")
  private String content;

  @ApiModelProperty("文章分类名")
  private String categoryName;

  @ApiModelProperty("缩略图")
  private String thumbnail;

  @ApiModelProperty("访问量")
  private Long viewCount;

  @ApiModelProperty("创建时间")
  private Date createTime;
}
