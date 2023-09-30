package com.ezhixuan.xuan_framework.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 角色信息表(Role)表实体类
 *
 * @author Ezhixuan
 * @since 2023-10-01 00:21:14
 */
@ApiModel("Role")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")
public class Role implements Serializable {

  private static final long serialVersionUID = 1L;

  // 角色ID
  @ApiModelProperty("角色ID")
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  // 角色名称
  @ApiModelProperty("角色名称")
  @TableField(value = "role_name")
  private String roleName;
  // 角色权限字符串
  @ApiModelProperty("角色权限字符串")
  @TableField(value = "role_key")
  private String roleKey;
  // 显示顺序
  @ApiModelProperty("显示顺序")
  @TableField(value = "role_sort")
  private Integer roleSort;
  // 角色状态（0正常 1停用）
  @ApiModelProperty("角色状态（0正常 1停用）")
  private String status;
  // 删除标志（0代表存在 1代表删除）
  @ApiModelProperty("删除标志（0代表存在 1代表删除）")
  @TableField(value = "del_flag")
  private String delFlag;
  // 创建者
  @ApiModelProperty("创建者")
  @TableField(value = "create_by")
  private Long createBy;
  // 创建时间
  @ApiModelProperty("创建时间")
  @TableField(value = "create_time")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createTime;
  // 更新者
  @ApiModelProperty("更新者")
  @TableField(value = "update_by")
  private Long updateBy;
  // 更新时间
  @ApiModelProperty("更新时间")
  @TableField(value = "update_time")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date updateTime;
  // 备注
  @ApiModelProperty("备注")
  private String remark;
}
