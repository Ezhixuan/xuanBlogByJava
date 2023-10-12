package com.ezhixuan.xuan_framework.strategy.context;

import com.ezhixuan.xuan_framework.strategy.UploadService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: xuanBlog
 * @description: 上传策略
 * @author: Mr.Xuan
 * @create: 2023-10-12 16:30
 */
@Service
public class UploadStrategyContext {
    
    @Value("${blog.upload}")
    private String upload;
    
    private String url;
    @Autowired
    private List<UploadService> uploadServiceMap;
    
    private UploadService getUploadService() {
        return uploadServiceMap.stream().filter(uploadService -> uploadService.check().equals(upload)).findFirst().get();
    }
    
    public String executeUploadStrategy(MultipartFile file) {
        return getUploadService().upload(file);
    }
}
