package com.ezhixuan.xuan_framework.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: xuanBlog
 * @description: log
 * @author: Mr.Xuan
 * @create: 2023-09-29 14:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
    String businessName();
    
}
