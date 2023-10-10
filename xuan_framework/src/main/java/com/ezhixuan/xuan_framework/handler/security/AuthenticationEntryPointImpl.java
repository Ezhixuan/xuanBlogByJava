package com.ezhixuan.xuan_framework.handler.security;

import cn.hutool.json.JSONUtil;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: xuanBlog
 * @description: 认证失败异常处理器
 * @author: Mr.Xuan
 * @create: 2023-09-27 20:53
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    // 打印异常信息
    authException.printStackTrace();
    ResponseResult result;
    // 根据不同的异常类型，返回不同的错误信息
    if (authException instanceof BadCredentialsException) {
      result =
          ResponseResult.errorResult(
              AppHttpCodeEnum.LOGIN_FAILURE.getCode(), authException.getMessage());
    } else if (authException instanceof InsufficientAuthenticationException) {
      result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
    } else if(authException instanceof InternalAuthenticationServiceException){
      result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_USER_ERROR);
    }
    else {
      result = ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR.getCode(), "认证或授权失败");
    }
    // 将错误信息返回给前端
    String jsonStr = JSONUtil.toJsonStr(result);
    WebUtils.renderString(response, jsonStr);
  }
}
