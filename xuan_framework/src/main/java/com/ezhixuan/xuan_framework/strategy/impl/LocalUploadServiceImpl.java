package com.ezhixuan.xuan_framework.strategy.impl;

import com.ezhixuan.xuan_framework.UploadEnum;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import java.io.File;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: xuanBlog
 * @description: 本地上传
 * @author: Mr.Xuan
 * @create: 2023-10-12 15:50
 */
@Service("localUploadServiceImpl")
public class LocalUploadServiceImpl extends AbstractUploadServiceImpl {

  @Override
  protected String getKey() {
    return UploadEnum.LOCAL_UPLOAD.getPath();
  }

  @Override
  public String check() {
    return CommonConstant.UPLOAD_LOCAL;
  }

  @Override
  @SneakyThrows
  protected String uploadFile(MultipartFile file, String key) {
    key = getKey() + "\\" + key;
    file.transferTo(new File(key));
    return key;
  }
}
