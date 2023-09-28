package com.ezhixuan.xuan_framework.domain.enums;

public enum AppHttpCodeEnum {

  // 成功段200
  SUCCESS(200, "操作成功"),
  // 400段
  NEED_LOGIN(401, "需要登录后操作"),
  LOGIN_AUTH_FAILURE(401, "账号或密码错误，请重新登录"),
  LOGIN_PASSWORD_ERROR(401, "密码错误"),
  LOGIN_USER_ERROR(401, "用户不存在"),
  LOGIN_FAILURE(401, "登录失败"),
  LOGIN_DENIED_FAILURE(403, "无权限操作"),
  // 500段
  SERVER_ERROR(500, "服务器异常"),
  DATA_NOT_EXIST(500, "数据不存在");

  int code;
  String message;

  AppHttpCodeEnum(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
