package com.ezhixuan.xuan_framework.service.impl;

import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.service.UploadService;
import com.ezhixuan.xuan_framework.utils.TencentCosUtil;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: xuanBlog
 * @description: 上传实现类
 * @author: Mr.Xuan
 * @create: 2023-09-29 11:10
 */
@Service("uploadService")
public class UploadServiceImpl implements UploadService{

    @Resource private TencentCosUtil tencentCosUtil;
    
    /**
     * 图片上传
     *
     * @param img
     * @return
     */
    @Override
    public ResponseResult<String> upload(MultipartFile img) {
        String upload = tencentCosUtil.upload(img);
        return ResponseResult.okResult(upload);
    }
}
