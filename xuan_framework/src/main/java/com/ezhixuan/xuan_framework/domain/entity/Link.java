package com.ezhixuan.xuan_framework.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 友链(Link)表实体类
 *
 * @author makejava
 * @since 2023-09-25 16:48:42
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("xuan_link")
public class Link  {
    @TableId
    private Long id;
    
    private String name;
    
    private String logo;
    
    private String description;
    private String address;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableLogic
    private Integer delFlag;
    
}
