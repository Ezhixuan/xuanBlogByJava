package com.ezhixuan.xuan_blog.controller;

import com.ezhixuan.xuan_framework.annotation.Log;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.utils.TencentCosUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: xuanBlog
 * @description: 上传控制器
 * @author: Mr.Xuan
 * @create: 2023-09-29 11:07
 */
@RestController
@Api(tags = "上传控制器")
public class UploadController {

    @Resource private TencentCosUtil tencentCosUtil;

    @Log(businessName = "上传图片")
    @ApiOperation("上传图片")
    @PostMapping("/upload")
    public ResponseResult<String> upload(MultipartFile img){
        return ResponseResult.okResult(tencentCosUtil.upload(img));
    }
}
