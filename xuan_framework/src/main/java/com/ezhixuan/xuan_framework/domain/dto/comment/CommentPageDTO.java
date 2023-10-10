package com.ezhixuan.xuan_framework.domain.dto.comment;

import com.ezhixuan.xuan_framework.domain.dto.PageDTO;
import com.ezhixuan.xuan_framework.handler.validated.Select;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @program: xuanBlog
 * @description: 评论分页查询
 * @author: Mr.Xuan
 * @create: 2023-09-28 10:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评论分页查询")
public class CommentPageDTO extends PageDTO {
  @NotNull(message = "文章id不能为空", groups = Select.class)
  @ApiModelProperty("文章id")
  private Long articleId;
}
