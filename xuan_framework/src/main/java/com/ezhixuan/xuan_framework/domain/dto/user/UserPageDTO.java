package com.ezhixuan.xuan_framework.domain.dto.user;

import com.ezhixuan.xuan_framework.domain.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 用户分页dto
 * @author: Mr.Xuan
 * @create: 2023-10-03 16:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户分页DTO")
public class UserPageDTO extends PageDTO {
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("手机号")
    private String phonenumber;
    @ApiModelProperty("状态")
    private String status;
}
