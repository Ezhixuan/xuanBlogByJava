package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.domain.dto.tag.TagDTO;
import com.ezhixuan.xuan_framework.domain.dto.tag.TagPageDTO;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.tag.TagVo;
import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Select;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import com.ezhixuan.xuan_framework.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
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
  @GetMapping("/list")
  public ResponseResult<PageVo> selectTagPageSys(TagPageDTO tagPageDTO) {
    return tagService.selectTagPageSys(tagPageDTO);
  }

  @ApiOperation("添加标签")
  @PostMapping
  public ResponseResult<String> insertTagSys(@RequestBody @Validated(Insert.class) TagDTO tagDTO) {
    return tagService.insertTagSys(tagDTO);
  }

  @ApiOperation("删除标签")
  @DeleteMapping("/{id}")
  public ResponseResult<String> deleteTagByIdSys(
      @PathVariable("id") @Validated(Update.class) List<Long> ids) {
    return tagService.deleteTagByIdSys(ids);
  }

  @ApiOperation("获取标签")
  @GetMapping("/{id}")
  public ResponseResult<TagVo> selectTagByIdSys(
      @PathVariable @Validated @NotNull(message = "标签id不能为空", groups = Select.class) Long id) {
    return tagService.selectTagByIdSys(id);
  }

  @ApiOperation("更新标签")
  @PutMapping
  public ResponseResult<String> updateTagSys(@RequestBody @Validated(Update.class) TagDTO tagDTO) {
    return tagService.updateTagSys(tagDTO);
  }

  @ApiOperation("获取所有标签")
  @GetMapping("/listAllTag")
  public ResponseResult<List<TagVo>> selectTagListSys() {
    return tagService.selectTagListSys();
  }
}
