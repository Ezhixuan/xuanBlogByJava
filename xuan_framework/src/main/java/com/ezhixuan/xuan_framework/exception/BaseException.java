package com.ezhixuan.xuan_framework.exception;
/**
 * @program: xuanBlog
 * @description: 基础异常
 * @author: Mr.Xuan
 * @create: 2023-09-25 15:39
 */
public class BaseException extends RuntimeException{

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }
}
