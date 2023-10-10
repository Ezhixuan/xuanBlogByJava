package com.ezhixuan.xuan_framework.domain.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 角色信息返回
 * @author: Mr.Xuan
 * @create: 2023-10-03 14:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("角色信息返回")
public class RoleVo {
    @ApiModelProperty("角色ID")
    private Long id;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("角色权限字符串")
    private String roleKey;
    @ApiModelProperty("显示顺序")
    private Integer roleSort;
    @ApiModelProperty("角色状态（0正常 1停用）")
    private String status;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("备注")
    private String remark;
}
