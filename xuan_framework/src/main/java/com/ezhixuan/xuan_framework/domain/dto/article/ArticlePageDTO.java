package com.ezhixuan.xuan_framework.domain.dto.article;

import com.ezhixuan.xuan_framework.domain.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 文章分页查询
 * @author: Mr.Xuan
 * @create: 2023-09-24 22:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("文章分页查询")
public class ArticlePageDTO extends PageDTO {
    
    @ApiModelProperty("分类id")
    private Long categoryId;
    
}
