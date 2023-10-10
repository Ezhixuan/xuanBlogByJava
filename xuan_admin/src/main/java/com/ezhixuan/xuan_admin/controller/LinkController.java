package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.dto.link.LinkDTO;
import com.ezhixuan.xuan_framework.domain.dto.link.LinkPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Link;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.handler.validated.Insert;
import com.ezhixuan.xuan_framework.handler.validated.Update;
import com.ezhixuan.xuan_framework.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @program: xuanBlog
 * @description: 友链控制器
 * @author: Mr.Xuan
 * @create: 2023-10-03 18:56
 */
@Api(tags = "友链控制器")
@RestController
@RequestMapping("content/link")
public class LinkController {

  @Resource private LinkService linkService;

  @Log(businessName = "获取友链列表")
  @GetMapping("/list")
  @ApiOperation("获取友链列表")
  public ResponseResult<PageVo> selectLinkPageSys(LinkPageDTO linkDTO) {
    return linkService.selectLinkPageSys(linkDTO);
  }

  @Log(businessName = "添加友链")
  @PostMapping
  @ApiOperation("添加友链")
  public ResponseResult<String> insertLinkSys(@RequestBody @Validated(Insert.class) LinkDTO linkDto) {
    return linkService.insertLinkSys(linkDto);
  }

  @Log(businessName = "根据id获取友链信息")
  @GetMapping("/{id}")
  @ApiModelProperty("根据id获取友链信息")
  public ResponseResult<Link> selectLinkByIdSys(@PathVariable @Validated @NotNull Long id) {
    return ResponseResult.okResult(linkService.getById(id));
  }

  @Log(businessName = "修改友链信息")
  @PutMapping
  @ApiModelProperty("修改友链信息")
  public ResponseResult<String> updateLinkSys(@RequestBody @Validated(Update.class) LinkDTO linkDto) {
    return linkService.updateLinkSys(linkDto);
  }

  @Log(businessName = "删除友链")
  @DeleteMapping("/{id}")
  @ApiModelProperty("删除友链")
  public ResponseResult<String> deleteLinkByIdSys(
      @PathVariable("id") @Validated @NotEmpty(message = "请选择需要删除的友链", groups = Insert.class)
          List<Long> ids) {
    linkService.removeByIds(ids);
    return ResponseResult.SUCCESS;
  }
}
