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
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author Ezhixuan
 * @since 2023-10-03 17:28:21
 */
@ApiModel("UserRole")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_role")
public class UserRole implements Serializable {

  private static final long serialVersionUID = 1L;

  // 用户ID
  @ApiModelProperty("用户ID")
  @TableId(value = "user_id", type = IdType.INPUT)
  private Long userId;
  // 角色ID
  @ApiModelProperty("角色ID")
  @TableField(value = "role_id")
  private Long roleId;
}
