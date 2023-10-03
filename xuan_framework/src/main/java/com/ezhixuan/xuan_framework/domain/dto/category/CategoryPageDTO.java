package com.ezhixuan.xuan_framework.domain.dto.category;

import com.ezhixuan.xuan_framework.domain.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 分类分页查询DTO
 * @author: Mr.Xuan
 * @create: 2023-10-03 18:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分类分页查询DTO")
public class CategoryPageDTO extends PageDTO{

    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("分类状态（0正常 1停用）")
    private String status;
}
