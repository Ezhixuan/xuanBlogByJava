package com.ezhixuan.xuan_framework.domain.dto.user;

import com.ezhixuan.xuan_framework.handler.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "用户id不能为空", groups = {Update.class})
    private Long id;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空", groups = {Update.class})
    private String nickName;

    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式不正确", groups = {Update.class})
    private String email;

    @ApiModelProperty("性别")
    @NotBlank(message = "性别不能为空", groups = {Update.class})
    private String sex;

    @ApiModelProperty("头像")
    @NotBlank(message = "头像不能为空", groups = {Update.class})
    private String avatar;

}
