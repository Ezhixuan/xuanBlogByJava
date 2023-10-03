package com.ezhixuan.xuan_framework.domain.dto.role;

import com.ezhixuan.xuan_framework.domain.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 角色分页查询DTO
 * @author: Mr.Xuan
 * @create: 2023-10-03 14:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("角色分页查询DTO")
public class RolePageDTO  extends PageDTO {
    @ApiModelProperty("角色名称")
    private String roleName;
    // 角色权限字符串
    @ApiModelProperty("角色状态（0正常 1停用）")
    private String status;
}
