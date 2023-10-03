package com.ezhixuan.xuan_framework.domain.dto.link;

import com.ezhixuan.xuan_framework.domain.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 友链分页DTO
 * @author: Mr.Xuan
 * @create: 2023-10-03 18:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("友链分页DTO")
public class LinkPageDTO extends PageDTO {

    @ApiModelProperty("友链名称")
    private String name;
    @ApiModelProperty("友链状态")
    private String status;
}
