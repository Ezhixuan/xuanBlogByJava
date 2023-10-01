package com.ezhixuan.xuan_framework.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: xiaomo-take-out
 * @description: 腾讯云Cos
 * @author: Mr.Xuan
 * @create: 2023-09-08 10:13
 */
@Component
@Slf4j
@Data
@ConfigurationProperties("blog.txcos")
public class TencentCosProperties {
  private String tmpSecretId;
  private String tmpSecretKey;
  private String bucketName;
  private String region;
  
}
