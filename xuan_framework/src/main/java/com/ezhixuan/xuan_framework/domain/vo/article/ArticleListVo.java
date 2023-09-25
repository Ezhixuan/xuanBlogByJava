package com.ezhixuan.xuan_framework.domain.vo.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("文章查询返回")
public class ArticleListVo {
    
    @ApiModelProperty("文章id")
    private Long id;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("文章摘要")
    private String summary;
    @ApiModelProperty("所属分类名")
    private String categoryName;
    @ApiModelProperty("缩略图")
    private String thumbnail;
    @ApiModelProperty("访问量")
    private Long viewCount;
    @ApiModelProperty("创建时间")
    private Date createTime;


}