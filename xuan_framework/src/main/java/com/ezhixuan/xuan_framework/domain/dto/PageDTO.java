package com.ezhixuan.xuan_framework.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 分页查询
 * @author: Mr.Xuan
 * @create: 2023-09-24 22:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
  @ApiModelProperty("页数")
  private Integer pageNum;

  @ApiModelProperty("每页条数")
  private Integer pageSize;

  /** 检查参数 */
  public void check() {
    if (this.pageNum == null || this.pageNum < 0) {
      this.pageNum = 1;
    }
    if (this.pageSize == null || this.pageSize < 0 || this.pageSize > 20) {
      this.pageSize = 10;
    }
  }
}
