package com.ezhixuan.xuan_framework.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: xuanBlog
 * @description: 分页返回
 * @author: Mr.Xuan
 * @create: 2023-09-25 15:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分页返回")
public class PageVo {

  @ApiModelProperty("数据")
  private List rows;

  @ApiModelProperty("总数")
  private Long total;
}
