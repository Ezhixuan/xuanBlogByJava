package com.ezhixuan.xuan_framework.strategy.impl;

import com.ezhixuan.xuan_framework.UploadEnum;
import com.ezhixuan.xuan_framework.config.properties.TencentCosProperties;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import javax.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: xuanBlog
 * @description: 上传实现类
 * @author: Mr.Xuan
 * @create: 2023-09-29 11:10
 */
@Service("cosUploadServiceImpl")
public class COSUploadServiceImpl extends AbstractUploadServiceImpl {

  @Override
  protected String getKey() {
    return UploadEnum.COS_UPLOAD.getPath();
  }

  @Resource private TencentCosProperties tencentCosProperties;

  @Override
  public String check() {
    return CommonConstant.UPLOAD_COS;
  }

  @Override
  @SneakyThrows
  protected String uploadFile(MultipartFile file, String key) {
    key = getKey() + "/" + key;
    COSClient register = register();
    register.putObject(tencentCosProperties.getBucketName(), key, file.getInputStream(), null);
    return "https://"
        + tencentCosProperties.getBucketName()
        + ".cos."
        + tencentCosProperties.getRegion()
        + ".myqcloud.com/"
        + key;
  }

  private COSClient register() {
    // 创建COSClient实例
    ClientConfig clientConfig = new ClientConfig(new Region(tencentCosProperties.getRegion()));
    clientConfig.setHttpProtocol(HttpProtocol.https);
    return new COSClient(
        new BasicCOSCredentials(
            tencentCosProperties.getTmpSecretId(), tencentCosProperties.getTmpSecretKey()),
        clientConfig);
  }
}
