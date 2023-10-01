package com.ezhixuan.xuan_framework.domain.dto.tag;

import com.ezhixuan.xuan_framework.domain.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 查询tag列表
 * @author: Mr.Xuan
 * @create: 2023-10-01 14:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor()
@ApiModel("查询tag列表")
public class TagPageDTO extends PageDTO {

    @ApiModelProperty("标签名")
    private String name;

    @ApiModelProperty("备注")
    private String remark;
}
