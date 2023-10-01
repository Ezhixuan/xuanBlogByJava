package com.ezhixuan.xuan_framework.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 返回分类名称
 * @author: Mr.Xuan
 * @create: 2023-09-24 18:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("返回分类名称")
public class CategoryVo {
    @ApiModelProperty("分类id")
    private Long id;
    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("分类描述")
    private String description;
}
