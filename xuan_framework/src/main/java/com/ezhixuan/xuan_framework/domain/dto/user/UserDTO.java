package com.ezhixuan.xuan_framework.domain.dto.user;

import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("新增角色DTO")
public class UserDTO {

  @ApiModelProperty("用户id")
  @NotNull(
      message = "用户id不能为空",
      groups = {Update.class})
  private Long id;

  @ApiModelProperty("用户昵称")
  @NotBlank(
      message = "用户昵称不能为空",
      groups = {Insert.class, Update.class})
  private String nickName;

  @ApiModelProperty("手机号")
  @NotBlank(
      message = "手机号不能为空",
      groups = {Insert.class, Update.class})
  private String phonenumber;

  @ApiModelProperty("邮箱")
  @NotBlank(
      message = "邮箱不能为空",
      groups = {Insert.class, Update.class})
  private String email;

  @ApiModelProperty("用户名")
  @NotBlank(
      message = "用户名不能为空",
      groups = {Insert.class, Update.class})
  private String userName;

  @ApiModelProperty("用户密码")
  @NotBlank(
      message = "用户密码不能为空",
      groups = {Insert.class, Update.class})
  private String password;

  @ApiModelProperty("性别")
  @NotBlank(
      message = "性别不能为空",
      groups = {Insert.class, Update.class})
  private String sex;

  @ApiModelProperty("状态(0 正常 1 停用)")
  @NotBlank(
      message = "状态不能为空",
      groups = {Insert.class, Update.class})
  private String status;

  @ApiModelProperty("关联角色id")
  @NotEmpty(
      message = "关联角色id不能为空",
      groups = {Insert.class, Update.class})
  private List<Long> roleIds;
}
