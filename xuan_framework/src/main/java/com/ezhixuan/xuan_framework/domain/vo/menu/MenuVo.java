package com.ezhixuan.xuan_framework.domain.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 菜单信息返回
 * @author: Mr.Xuan
 * @create: 2023-10-02 22:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜单信息返回")
public class MenuVo {
  @ApiModelProperty("图标")
  private String icon;

  @ApiModelProperty("菜单id")
  private Long id;

  @ApiModelProperty("是否为外链")
  private Integer isFrame;

  @ApiModelProperty("菜单名")
  private String menuName;

  @ApiModelProperty("菜单类型（M目录 C菜单 F按钮）")
  private String menuType;

  @ApiModelProperty("显示顺序")
  private Integer orderNum;

  @ApiModelProperty("父菜单id")
  private Long parentId;

  @ApiModelProperty("路由地址")
  private String path;

  @ApiModelProperty("权限标识")
  private String perms;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("菜单状态（0正常 1停用）")
  private String status;

  @ApiModelProperty("菜单状态（0显示 1隐藏）")
  private String visible;

  @ApiModelProperty("组件路径")
  private String component;

  @ApiModelProperty("创建时间")
  private Date createTime;
}
