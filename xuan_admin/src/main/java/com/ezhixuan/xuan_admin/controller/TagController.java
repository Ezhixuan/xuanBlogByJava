package com.ezhixuan.xuan_admin.controller;


import com.ezhixuan.xuan_framework.domain.dto.tag.TagDTO;
import com.ezhixuan.xuan_framework.domain.dto.tag.TagPageDTO;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.tag.TagVo;
import com.ezhixuan.xuan_framework.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @program: xuanBlog
 * @description: 标签控制器
 * @author: Mr.Xuan
 * @create: 2023-09-30 17:37
 */
@Api(tags = "标签控制器")
@RestController
@RequestMapping("content/tag")
public class TagController {
    
    
    @Resource private TagService tagService;
    
    @ApiOperation("标签列表")
    @GetMapping("list")
    public ResponseResult<PageVo> tagList(TagPageDTO tagPageDTO){
        return tagService.tagList(tagPageDTO);
    }

    @ApiOperation("添加标签")
    @PostMapping()
    public ResponseResult<String> addTag(@RequestBody TagDTO tagDTO){
        return tagService.save(tagDTO);
    }

    @ApiOperation("删除标签")
    @DeleteMapping("{id}")
    public ResponseResult<String> deleteTag(@PathVariable("id") List<Long> ids){
        return tagService.deleteTag(ids);
    }
    
    @ApiOperation("获取标签")
    @GetMapping("{id}")
    public ResponseResult<TagVo> getTag(@PathVariable Long id){
        return tagService.getTag(id);
    }

    @ApiOperation("更新标签")
    @PutMapping()
    public ResponseResult<String> updateTag(@RequestBody TagDTO tagDTO){
        return tagService.updateTag(tagDTO);
    }
    
    @ApiOperation("获取所有标签")
    @GetMapping("listAllTag")
    public ResponseResult<List<TagVo>> listAllTag(){
        return tagService.listAllTag();
    }
    
}
