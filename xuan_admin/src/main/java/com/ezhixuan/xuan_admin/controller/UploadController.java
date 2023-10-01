package com.ezhixuan.xuan_admin.controller;

import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.service.UploadService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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

  @Resource private UploadService uploadService;

  @PostMapping()
  public ResponseResult<String> upload(MultipartFile img){
    return uploadService.upload(img);
  }
}
