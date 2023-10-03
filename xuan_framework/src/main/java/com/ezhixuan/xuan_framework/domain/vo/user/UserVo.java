package com.ezhixuan.xuan_framework.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: xuanBlog
 * @description: 后台用户信息返回
 * @author: Mr.Xuan
 * @create: 2023-10-03 16:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("后台用户信息返回")
public class UserVo {
  @ApiModelProperty("头像")
  private String avatar;

  @ApiModelProperty("创建时间")
  private Date createTime;

  @ApiModelProperty("邮箱")
  private String email;

  @ApiModelProperty("用户id")
  private Long id;

  @ApiModelProperty("昵称")
  private String nickName;

  @ApiModelProperty("手机号码")
  private String phonenumber;

  @ApiModelProperty("性别")
  private String sex;

  @ApiModelProperty("状态")
  private String status;

  @ApiModelProperty("更新人")
  private Long updateBy;

  @ApiModelProperty("更新时间")
  private Date updateTime;

  @ApiModelProperty("用户名")
  private String userName;

}
