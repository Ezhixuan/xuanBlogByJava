package com.ezhixuan.xuan_framework.domain.vo.user;

import com.ezhixuan.xuan_framework.domain.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
  private User user;

  @ApiModelProperty("角色信息")
  private List<String> roles;

  @ApiModelProperty("权限信息")
  private List<String> permissions;
}
