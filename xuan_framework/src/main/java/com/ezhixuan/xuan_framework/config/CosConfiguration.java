package com.ezhixuan.xuan_framework.config;

import com.ezhixuan.xuan_framework.config.properties.TencentCosProperties;
import com.ezhixuan.xuan_framework.utils.TencentCosUtil;
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
    TencentCosUtil tencentCosUtil = new TencentCosUtil(
            tencentCosProperties.getTmpSecretId(),
            tencentCosProperties.getTmpSecretKey(),
            tencentCosProperties.getBucketName(),
            tencentCosProperties.getRegion());
    return tencentCosUtil;
  }
}
