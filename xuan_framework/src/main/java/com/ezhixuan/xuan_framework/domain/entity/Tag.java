package com.ezhixuan.xuan_framework.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * 标签(Tag)表实体类
 *
 * @author Ezhixuan
 * @since 2023-09-30 17:21:43
 */
@ApiModel("Tag")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("xuan_tag")
public class Tag implements Serializable {

  private static final long serialVersionUID = 1L;

  // id
  @ApiModelProperty("id")
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  // 标签名
  @ApiModelProperty("标签名")
  private String name;
  // createBy
  @ApiModelProperty("createBy")
  @TableField(value = "create_by", fill = FieldFill.INSERT)
  private Long createBy;
  // createTime
  @ApiModelProperty("createTime")
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createTime;
  // updateBy
  @ApiModelProperty("updateBy")
  @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
  private Long updateBy;
  // updateTime
  @ApiModelProperty("updateTime")
  @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date updateTime;
  // 删除标志（0代表未删除，1代表已删除）
  @ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
  @TableField(value = "del_flag")
  private Integer delFlag;
  // 备注
  @ApiModelProperty("备注")
  private String remark;
}
