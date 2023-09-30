package com.ezhixuan.xuan_framework.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 后台用户信息返回
 * @author: Mr.Xuan
 * @create: 2023-09-30 21:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor()
@Builder
@ApiModel("后台用户信息返回")
public class SysUserInfo {
  @ApiModelProperty("用户信息")
  private UserInfoVo user;

  @ApiModelProperty("角色信息")
  private List<String> roles;

  @ApiModelProperty("权限信息")
  private List<String> permissions;
}
