package com.ezhixuan.xuan_framework.domain.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ezhixuan.xuan_framework.domain.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("新增角色DTO")
public class UserSaveDTO extends User {
    @TableField(exist = false)
    @ApiModelProperty("关联菜单id")
    private List<Long> roleIds;
}