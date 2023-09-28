package com.ezhixuan.xuan_framework.exception;

import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @program: xuanBlog
 * @description: 基础异常
 * @author: Mr.Xuan
 * @create: 2023-09-25 15:39
 */
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {

  private Integer code;
  private String msg;
  public BaseException(AppHttpCodeEnum appHttpCodeEnum) {
    this.code = appHttpCodeEnum.getCode();
    this.msg = appHttpCodeEnum.getMessage();
  }
}
