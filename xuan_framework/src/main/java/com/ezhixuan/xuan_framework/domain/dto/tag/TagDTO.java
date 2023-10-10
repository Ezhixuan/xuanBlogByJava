package com.ezhixuan.xuan_framework.domain.dto.tag;

import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description:
 * @author: Mr.Xuan
 * @create: 2023-10-01 15:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("标签信息")
public class TagDTO {

    @ApiModelProperty("标签id")
    @NotNull(message = "标签id不能为空",groups = {Update.class})
    private Long id;

    @ApiModelProperty("标签名")
    @NotBlank(message = "标签名不能为空",groups = {Insert.class, Update.class})
    private String name;
    @ApiModelProperty("备注")
    private String remark;
}
