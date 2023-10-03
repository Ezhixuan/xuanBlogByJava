package com.ezhixuan.xuan_framework.domain.dto.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 新增分类DTO
 * @author: Mr.Xuan
 * @create: 2023-10-03 18:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("新增分类DTO")
public class CategorySaveDTO {

  @ApiModelProperty("状态（0正常 1停用）")
  private String status;

  @ApiModelProperty("分类名称")
  private String name;

  @ApiModelProperty("分类描述")
  private String description;
}
