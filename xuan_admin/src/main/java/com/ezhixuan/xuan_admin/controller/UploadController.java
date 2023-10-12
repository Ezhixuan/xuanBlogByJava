package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.strategy.context.UploadStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: xuanBlog
 * @description: 上传控制器
 * @author: Mr.Xuan
 * @create: 2023-10-01 16:35
 */
@Api(tags = "上传控制器")
@RestController
@RequestMapping("upload")
public class UploadController {

  @Resource private UploadStrategyContext uploadStrategyContext;

  @Log(businessName = "上传图片")
  @ApiOperation("上传图片")
  @PostMapping
  public ResponseResult<String> upload(MultipartFile img){
    String url = uploadStrategyContext.executeUploadStrategy(img);
    return ResponseResult.okResult(url);
  }
}
