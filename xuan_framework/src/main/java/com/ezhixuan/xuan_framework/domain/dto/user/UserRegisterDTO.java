package com.ezhixuan.xuan_framework.domain.dto.user;
/**
 * @program: xuanBlog
 * @description: 用户注册DTO
 * @author: Mr.Xuan
 * @create: 2023-09-29 12:15
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户登录DTO")
public class UserRegisterDTO {
  @ApiModelProperty("用户名")
  private String userName;

  @ApiModelProperty("密码")
  private String password;

  @ApiModelProperty("昵称")
  private String nickName;

  @ApiModelProperty("邮箱")
  private String email;
}
