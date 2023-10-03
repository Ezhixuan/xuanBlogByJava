package com.ezhixuan.xuan_framework.domain.vo.role;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @program: xuanBlog
 * @description: 角色信息返回
 * @author: Mr.Xuan
 * @create: 2023-10-03 14:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
    @ApiModelProperty("角色ID")
    private Long id;
    // 角色名称
    @ApiModelProperty("角色名称")
    private String roleName;
    // 角色权限字符串
    @ApiModelProperty("角色权限字符串")
    private String roleKey;
    // 显示顺序
    @ApiModelProperty("显示顺序")
    private Integer roleSort;
    // 角色状态（0正常 1停用）
    @ApiModelProperty("角色状态（0正常 1停用）")
    private String status;
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @ApiModelProperty("备注")
    private String remark;
}
