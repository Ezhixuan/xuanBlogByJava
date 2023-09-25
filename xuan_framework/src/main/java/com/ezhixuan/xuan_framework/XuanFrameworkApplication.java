package com.ezhixuan.xuan_framework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ezhixuan.xuan_framework.dao")
public class XuanFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(XuanFrameworkApplication.class, args);
    }

}
