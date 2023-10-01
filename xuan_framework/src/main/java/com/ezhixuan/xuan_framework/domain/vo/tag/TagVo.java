package com.ezhixuan.xuan_framework.domain.vo.tag;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 标签信息返回
 * @author: Mr.Xuan
 * @create: 2023-10-01 14:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {

  @ApiModelProperty("id")
  private Long id;
  // 标签名
  @ApiModelProperty("标签名")
  private String name;

  @ApiModelProperty("备注")
  private String remark;
}
