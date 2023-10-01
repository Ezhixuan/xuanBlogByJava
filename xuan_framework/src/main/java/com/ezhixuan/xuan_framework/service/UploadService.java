package com.ezhixuan.xuan_framework.service;


import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    /**
     * 图片上传
     * @param img
     * @return
     */
    ResponseResult<String> upload(MultipartFile img);
}
