package com.ezhixuan.xuan_framework.domain.vo.user;

import com.ezhixuan.xuan_framework.domain.entity.Role;
import com.ezhixuan.xuan_framework.domain.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 用户信息返回
 * @author: Mr.Xuan
 * @create: 2023-10-03 18:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("后台用户信息返回")
@Builder
public class UserQueryVo {
  @ApiModelProperty("角色列表")
  private List<Role> roles;

  @ApiModelProperty("角色ID列表")
  private List<Long> roleIds;

  @ApiModelProperty("用户信息")
  private User user;
}
