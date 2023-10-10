package com.ezhixuan.xuan_framework.domain.dto.user;
/**
 * @program: xuanBlog
 * @description: 用户注册DTO
 * @author: Mr.Xuan
 * @create: 2023-09-29 12:15
 */
import com.ezhixuan.xuan_framework.handler.validated.Insert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户登录DTO")
public class UserRegisterDTO {
  @ApiModelProperty("用户名")
  @NotBlank(message = "用户名不能为空", groups = Insert.class)
  private String userName;

  @ApiModelProperty("密码")
  @NotBlank(message = "密码不能为空", groups = Insert.class)
  private String password;

  @ApiModelProperty("昵称")
  @NotBlank(message = "昵称不能为空", groups = Insert.class)
  private String nickName;

  @ApiModelProperty("邮箱")
  @Email(message = "邮箱格式不正确", groups = Insert.class)
  private String email;
}
