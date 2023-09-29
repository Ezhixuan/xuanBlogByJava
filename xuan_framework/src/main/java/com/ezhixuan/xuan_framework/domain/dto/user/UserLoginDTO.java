package com.ezhixuan.xuan_framework.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String userName;
    @ApiModelProperty("密码")
    private String password;
}
