package com.ezhixuan.xuan_blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: xuanBlog
 * @description:
 * @author: Mr.Xuan
 * @create: 2023-09-23 16:53
 */
@SpringBootApplication
@MapperScan("com.ezhixuan.xuan_framework.dao")
@ComponentScan("com.ezhixuan")
@EnableScheduling
public class XuanBlogApplication {
  public static void main(String[] args) {
    SpringApplication.run(XuanBlogApplication.class, args);
  }
}
