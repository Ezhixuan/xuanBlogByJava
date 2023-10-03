package com.ezhixuan.xuan_framework.domain.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 菜单树返回
 * @author: Mr.Xuan
 * @create: 2023-10-03 14:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜单树返回")
public class MenuTreeVo {
  @ApiModelProperty("菜单id")
  private Long id;

  @ApiModelProperty("父菜单id")
  private Long parentId;

  @ApiModelProperty("子菜单")
  private List<MenuTreeVo> children;

  @ApiModelProperty("菜单名")
  private String label;
}
