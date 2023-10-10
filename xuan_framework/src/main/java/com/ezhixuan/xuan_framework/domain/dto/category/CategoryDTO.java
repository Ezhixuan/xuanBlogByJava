package com.ezhixuan.xuan_framework.domain.dto.category;

import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class CategoryDTO {
  
  @ApiModelProperty("分类id")
  @NotNull(
      message = "分类id不能为空",
      groups = {Update.class})
  private Long id;

  @ApiModelProperty("状态（0正常 1停用）")
  @NotBlank(
      message = "状态不能为空",
      groups = {Insert.class, Update.class})
  private String status;

  @ApiModelProperty("分类名称")
  @NotBlank(
      message = "分类名称不能为空",
      groups = {Insert.class, Update.class})
  private String name;

  @ApiModelProperty("分类描述")
  @NotBlank(
      message = "分类描述不能为空",
      groups = {Insert.class, Update.class})
  private String description;
}
