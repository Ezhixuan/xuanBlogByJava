package com.ezhixuan.xuan_framework.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 用户信息DTO
 * @author: Mr.Xuan
 * @create: 2023-09-29 11:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户信息传输")
public class UserInfoDTO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String nickName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("头像")
    private String avatar;

}
