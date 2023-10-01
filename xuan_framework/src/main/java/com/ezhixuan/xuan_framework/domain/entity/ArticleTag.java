package com.ezhixuan.xuan_framework.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author Ezhixuan
 * @since 2023-10-01 15:40:58
 */
@ApiModel("ArticleTag")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("xuan_article_tag")
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag implements Serializable {

  private static final long serialVersionUID = 1L;

  // 文章id
  @ApiModelProperty("文章id")
  @TableId(value = "article_id", type = IdType.AUTO)
  private Long articleId;
  // 标签id
  @ApiModelProperty("标签id")
  @TableField(value = "tag_id")
  private Long tagId;
}
