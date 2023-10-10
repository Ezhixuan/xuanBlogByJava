package com.ezhixuan.xuan_framework.domain.dto.role;

import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 角色DTO
 * @author: Mr.Xuan
 * @create: 2023-10-03 14:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("角色DTO")
public class RoleDTO {
  
  @ApiModelProperty("角色ID")
  @NotNull(message = "角色ID不能为空", groups = Update.class)
  private Long id;

  @ApiModelProperty("角色名称")
  @NotBlank(
      message = "角色名称不能为空",
      groups = {Update.class, Insert.class})
  private String roleName;

  @ApiModelProperty("角色权限字符串")
  private String roleKey;

  @ApiModelProperty("显示顺序")
  @NotNull(
      message = "显示顺序不能为空",
      groups = {Update.class, Insert.class})
  private Integer roleSort;

  @ApiModelProperty("角色状态（0正常 1停用）")
  @NotBlank(
      message = "角色状态不能为空",
      groups = {Update.class, Insert.class})
  private String status;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("关联菜单id")
  private List<Long> menuIds;
}
