package com.ezhixuan.xuan_framework.domain.dto.user;

import com.ezhixuan.xuan_framework.handler.validated.Select;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @program: xuanBlog
 * @description: 用户登录DTO
 * @author: Mr.Xuan
 * @create: 2023-09-27 19:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户登录DTO")
public class UserLoginDTO {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空", groups = {Select.class})
    private String userName;
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空", groups = {Select.class})
    private String password;
}
