package com.ezhixuan.xuan_framework.domain.dto.role;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ezhixuan.xuan_framework.domain.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 新增角色DTO
 * @author: Mr.Xuan
 * @create: 2023-10-03 14:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("新增角色DTO")
public class RoleSaveDTO extends Role {
    @TableField(exist = false)
    @ApiModelProperty("关联菜单id")
    private List<Long> menuIds;
}
