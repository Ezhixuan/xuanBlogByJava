package com.ezhixuan.xuan_framework.config;

import com.ezhixuan.xuan_framework.config.properties.TencentCosProperties;
import com.ezhixuan.xuan_framework.utils.TencentCosUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: xiaomo-take-out
 * @description:
 * @author: Mr.Xuan
 * @create: 2023-09-08 10:46
 */
@Configuration
@Slf4j
public class CosConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public TencentCosUtil tencentCosUtil(TencentCosProperties tencentCosProperties) {
    log.info("开始创建腾讯云文件上传对象{}", tencentCosProperties);
    // 创建COSClient实例
    ClientConfig clientConfig = new ClientConfig(new Region(tencentCosProperties.getRegion()));
    clientConfig.setHttpProtocol(HttpProtocol.https);
    COSClient cosClient =
        new COSClient(
            new BasicCOSCredentials(
                tencentCosProperties.getTmpSecretId(), tencentCosProperties.getTmpSecretKey()),
            clientConfig);
    return new TencentCosUtil(tencentCosProperties.getBucketName(), cosClient);
  }
}
