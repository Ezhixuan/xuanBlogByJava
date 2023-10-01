package com.ezhixuan.xuan_framework.domain.vo.article;

import com.ezhixuan.xuan_framework.domain.entity.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 后台文章信息返回
 * @author: Mr.Xuan
 * @create: 2023-10-01 22:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("后台文章信息返回")
public class ArticleSysVo extends Article {
  
  @ApiModelProperty("标签")
  private List<Long> tags;
  
}
