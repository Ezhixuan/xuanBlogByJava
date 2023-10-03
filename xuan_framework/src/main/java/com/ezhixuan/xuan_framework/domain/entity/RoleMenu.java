package com.ezhixuan.xuan_framework.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author Ezhixuan
 * @since 2023-10-03 14:11:37
 */
@ApiModel("RoleMenu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role_menu")
public class RoleMenu implements Serializable {

  private static final long serialVersionUID = 1L;

  // 角色ID
  @ApiModelProperty("角色ID")
  @TableId(value = "role_id", type = IdType.INPUT)
  private Long roleId;
  // 菜单ID
  @ApiModelProperty("菜单ID")
  @TableField(value = "menu_id")
  private Long menuId;
}
