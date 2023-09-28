package com.ezhixuan.xuan_framework.exception;
/**
 * @program: xuanBlog
 * @description: 普通异常
 * @author: Mr.Xuan
 * @create: 2023-09-25 15:38
 */
public class UserLoginException extends BaseException {

  public UserLoginException(Integer code,String msg) {
    super(code,msg);
  }
}
