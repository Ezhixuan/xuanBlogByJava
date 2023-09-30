package com.ezhixuan.xuan_framework.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2023-09-27 18:04:00
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User  {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String userName;
    private String nickName;
    private String password;
    private String type;
    private String status;
    private String email;
    private String phonenumber;
    private String sex;
    private String avatar;
    @TableField(fill = FieldFill.INSERT)//创建人的用户id
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)//创建时间
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)//更新人
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)//更新时间
    private Date updateTime;
    @TableLogic
    private Integer delFlag;
    
}
