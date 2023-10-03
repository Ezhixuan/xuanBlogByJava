package com.ezhixuan.xuan_framework.domain.dto.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 菜单列表传输
 * @author: Mr.Xuan
 * @create: 2023-10-02 21:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor()
@ApiModel("菜单列表传输")
public class MenuListDTO {

    @ApiModelProperty("菜单名")
    private String menuName;

    //菜单状态（0正常 1停用）
    @ApiModelProperty("菜单状态")
    private String status;
}
