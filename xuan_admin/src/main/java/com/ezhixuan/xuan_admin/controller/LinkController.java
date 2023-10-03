package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.domain.dto.link.LinkPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Link;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
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
    
    @GetMapping("list")
    @ApiOperation("获取友链列表")
    public ResponseResult<PageVo> queryList(LinkPageDTO linkDTO){
        return linkService.queryList(linkDTO);
    }

    @PostMapping()
    @ApiOperation("添加友链")
    public ResponseResult<String> save(@RequestBody Link link){
        link.setStatus(CommonConstant.LINK_STATUS_UNCHECKED);
        linkService.save(link);
        return ResponseResult.SUCCESS;
    }

    @GetMapping("{id}")
    @ApiModelProperty("根据id获取友链信息")
    public ResponseResult<Link> queryById(@PathVariable Long id){
        return ResponseResult.okResult(linkService.getById(id));
    }

    @PutMapping()
    @ApiModelProperty("修改友链信息")
    public ResponseResult<String> update(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.SUCCESS;
    }

    @DeleteMapping("{id}")
    @ApiModelProperty("删除友链")
    public ResponseResult<String> delete(@PathVariable("id") List<Long> ids){

        return linkService.remove(ids);
    }
}
