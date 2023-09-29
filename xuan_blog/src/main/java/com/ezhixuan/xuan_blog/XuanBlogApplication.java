package com.ezhixuan.xuan_blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @program: xuanBlog
 * @description:
 * @author: Mr.Xuan
 * @create: 2023-09-23 16:53
 */
@SpringBootApplication
@MapperScan("com.ezhixuan.xuan_framework.dao")
@ComponentScans({
  @ComponentScan("com.ezhixuan.xuan_framework.service"),
  @ComponentScan("com.ezhixuan.xuan_framework.dao"),
  @ComponentScan("com.ezhixuan.xuan_framework.config"),
  @ComponentScan("com.ezhixuan.xuan_framework.handler"),
  @ComponentScan("com.ezhixuan.xuan_framework.annotation"),
  @ComponentScan("com.ezhixuan.xuan_framework.aspect"),
  @ComponentScan("com.ezhixuan.xuan_framework.utils")
})

public class XuanBlogApplication {
  public static void main(String[] args) {
    SpringApplication.run(XuanBlogApplication.class, args);
  }
}
