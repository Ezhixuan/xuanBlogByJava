package com.ezhixuan.xuan_framework.handler;

import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: xuanBlog
 * @description: 全局异常处理机制
 * @author: Mr.Xuan
 * @create: 2023-09-25 15:44
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(BaseException.class)
  public ResponseResult exceptionHandler(BaseException ex) {
    log.error("异常信息：{}", ex);
    return ResponseResult.errorResult(403 , ex.getMessage());
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseResult exceptionHandler(SecurityException ex){
    log.error("异常信息:{}",ex);
    System.out.println(ex.getMessage());
    return ResponseResult.errorResult(403 , ex.getMessage());
  }
  
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseResult exceptionHandler(HttpMessageNotReadableException ex) {
    log.error("用户未携带token");
    log.error("异常信息：{}", ex);
    return ResponseResult.errorResult(500 , "用户未登录");
  }

  @ExceptionHandler(Exception.class)
  public ResponseResult exceptionHandler(Exception ex) {
    log.error("异常信息：{}", ex);
    return ResponseResult.errorResult(500 , ex.getMessage());
  }
}
