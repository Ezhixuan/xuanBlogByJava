package com.ezhixuan.xuan_framework.domain.vo.link;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 友链列表返回
 * @author: Mr.Xuan
 * @create: 2023-09-25 16:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("友链列表返回")
public class LinkListVo {
  @ApiModelProperty("友链id")
  private Long id;

  @ApiModelProperty("友联名称")
  private String name;

  @ApiModelProperty("logo")
  private String logo;

  @ApiModelProperty("描述")
  private String description;

  @ApiModelProperty("网址")
  private String address;
}
