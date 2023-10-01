package com.ezhixuan.xuan_framework.domain.dto.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description:
 * @author: Mr.Xuan
 * @create: 2023-10-01 15:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor()
@ApiModel("标签信息")
public class TagDTO {

    @ApiModelProperty("标签id")
    private Long id;

    @ApiModelProperty("标签名")
    private String name;
    @ApiModelProperty("备注")
    private String remark;
}
