package com.ezhixuan.xuan_framework.domain.dto.menu;

import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 菜单操作DTO
 * @author: Mr.Xuan
 * @create: 2023-10-09 18:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜单操作DTO")
public class MenuDTO {

  @NotNull(message = "友链id不能为空", groups = Update.class)
  private Long id;

  @ApiModelProperty("父菜单ID")
  private Long parentId;

  @NotBlank(message = "菜单名称不能为空", groups = {Insert.class,Update.class})
  @ApiModelProperty("菜单名")
  private String menuName;

  @ApiModelProperty("路由地址")
  private String path;

  @NotNull(message = "显示顺序不能为空", groups = {Insert.class,Update.class})
  @ApiModelProperty("显示顺序")
  private Integer orderNum;

  @ApiModelProperty("菜单类型（M目录 C菜单 F按钮）")
  private String menuType;

  @ApiModelProperty("菜单图标")
  private String icon;

  @ApiModelProperty("是否显示")
  private String visible;

  @ApiModelProperty("菜单状态（0正常 1停用）")
  private String status;

  @ApiModelProperty("组件路径")
  private String component;

  @ApiModelProperty("权限标识")
  private String perms;
}
