package com.ezhixuan.xuan_framework.domain.vo.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("热点文章返回")
public class HotArticleVo {
    @ApiModelProperty("文章id")
    private Long id;
    //标题
    @ApiModelProperty("文章标题")
    private String title;

    //访问量
    @ApiModelProperty("文章访问量")
    private Long viewCount;
}
