package com.ezhixuan.xuan_framework.strategy;


import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    /**
     * 图片上传
     * @param file
     * @return
     */
    String upload(MultipartFile file);

    String check();
}
