package com.ezhixuan.xuan_framework.domain.dto.role;

import com.ezhixuan.xuan_framework.handler.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: xuanBlog
 * @description: 角色状态DTO
 * @author: Mr.Xuan
 * @create: 2023-10-09 19:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色状态DTO")
public class RoleStatusDTO {

    @ApiModelProperty("角色ID")
    @NotNull(message = "角色ID不能为空",groups = Update.class)
    private Long id;
    @ApiModelProperty("角色状态（0正常 1停用）")
    @NotBlank(message = "角色状态不能为空",groups = Update.class)
    private String status;
}
