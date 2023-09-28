package com.ezhixuan.xuan_framework.handler.security;

import cn.hutool.json.JSONUtil;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: xuanBlog
 * @description: 登录失败处理器
 * @author: Mr.Xuan
 * @create: 2023-09-27 20:50
 */
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler{
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseResult responseResult = ResponseResult.okResult(AppHttpCodeEnum.LOGIN_FAILURE);
        String jsonStr = JSONUtil.toJsonStr(responseResult);
        WebUtils.renderString(response,jsonStr);

    }
}
