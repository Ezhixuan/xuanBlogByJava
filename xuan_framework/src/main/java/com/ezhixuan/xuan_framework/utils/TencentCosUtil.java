package com.ezhixuan.xuan_framework.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: xiaomo-take-out
 * @description: cos工具类
 * @author: Mr.Xuan
 * @create: 2023-09-08 11:47
 */
@Data
@AllArgsConstructor
@Slf4j
public class TencentCosUtil {

  private String bucketName;
  private COSClient cosClient;

  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  public Map<String, String> upload(MultipartFile file) {

    try {
      // 获取上传文件的输入流
      InputStream inputStream = file.getInputStream();
      // 对象键(Key)是对象在存储桶中的唯一标识。
      String substring =
          Objects.requireNonNull(file.getOriginalFilename())
              .substring(file.getOriginalFilename().lastIndexOf("."));
      String key = UUID.randomUUID().toString().replaceAll("-", "") + substring;
      // 对上传文件分组
      String dateUrl = new DateTime().toString("yyyy/MM/dd");
      key = dateUrl + "/" + key;

      ObjectMetadata objectMetadata = new ObjectMetadata();

      PutObjectRequest putObjectRequest =
          new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
      PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
      System.out.println(JSON.toJSONString(putObjectResult));
      // 返回上传文件路径
      StringBuilder stringBuilder = new StringBuilder("https://");
      stringBuilder
          .append(bucketName)
          .append(".cos.")
          .append(cosClient.getClientConfig().getRegion())
          .append(".myqcloud.com/")
          .append(key);
      HashMap<String, String> stringStringHashMap = new HashMap<>();
      stringStringHashMap.put("url", stringBuilder.toString());
      stringStringHashMap.put("key", key);

      return stringStringHashMap;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 删除文件
   *
   * @param url 路径
   */
  public void delete(String url) {
    cosClient.deleteObject(bucketName, url);
  }
}
