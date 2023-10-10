package com.ezhixuan.xuan_framework.domain.dto.link;

import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 友链添加DTO
 * @author: Mr.Xuan
 * @create: 2023-10-09 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("友链添加DTO")
public class LinkDTO {
  @NotNull(message = "友链id不能为空", groups = Update.class)
  private Long id;

  @NotBlank(message = "友链名称不能为空", groups = {Insert.class,Update.class})
  private String name;

  @NotBlank(message = "友链logo不能为空", groups = {Insert.class,Update.class})
  private String logo;

  @NotBlank(message = "友链描述不能为空", groups = {Insert.class,Update.class})
  private String description;

  @NotBlank(message = "友链地址不能为空", groups = {Insert.class,Update.class})
  private String address;

  @NotBlank(message = "友链状态不能为空", groups = {Insert.class,Update.class})
  private String status;
}
