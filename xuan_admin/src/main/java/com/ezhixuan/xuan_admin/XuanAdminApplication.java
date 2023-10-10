package com.ezhixuan.xuan_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: xuanBlog
 * @description:
 * @author: Mr.Xuan
 * @create: 2023-09-23 16:52
 */
@SpringBootApplication
@ComponentScan({"com.ezhixuan.xuan_framework","com.ezhixuan.xuan_admin"})
public class XuanAdminApplication {

  public static void main(String[] args) {
    SpringApplication.run(XuanAdminApplication.class, args);
  }
}
