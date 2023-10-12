package com.ezhixuan.xuan_framework.strategy.impl;

import cn.hutool.core.lang.UUID;
import com.ezhixuan.xuan_framework.strategy.UploadService;
import java.util.Objects;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: xuanBlog
 * @description: 抽象上传
 * @author: Mr.Xuan
 * @create: 2023-10-12 22:09
 */
public abstract class AbstractUploadServiceImpl implements UploadService{
    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @Override
    @SneakyThrows
    public String upload(MultipartFile file) {
        String substring =
                Objects.requireNonNull(file.getOriginalFilename())
                        .substring(file.getOriginalFilename().lastIndexOf("."));
        String key = UUID.randomUUID().toString().replaceAll("-", "") + substring;
        return uploadFile(file, key);
    }

    /**
     * 检查
     * @return
     */
    @Override
    public String check() {
        return null;
    }

    protected abstract String uploadFile(MultipartFile file, String key);
    
    protected abstract String getKey();
}
