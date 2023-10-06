package com.ezhixuan.xuan_blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @program: xuanBlog
 * @description: 接口文档配置类
 * @author: Mr.Xuan
 * @create: 2023-09-30 17:57
 */
@Configuration
@Slf4j
public class Knife4jConfig {
  @Bean(value = "docket")
  public Docket docket() {
    log.info("正在生成接口文档");
    ApiInfo apiInfo =
        new ApiInfoBuilder()
            .title("xuanBlog项目接口文档")
            .version("1.0")
            .description("xuanBlog项目接口文档")
            .build();
    Docket docket =
        new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.ezhixuan.xuan_blog.controller"))
            .paths(PathSelectors.any())
            .build();
    return docket;
  }
}
