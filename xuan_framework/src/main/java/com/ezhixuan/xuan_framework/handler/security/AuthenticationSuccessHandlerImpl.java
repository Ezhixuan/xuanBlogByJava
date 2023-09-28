package com.ezhixuan.xuan_framework.handler.security;

import cn.hutool.json.JSONUtil;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.utils.WebUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @program: xuanBlog
 * @description: 登录成功处理器
 * @author: Mr.Xuan
 * @create: 2023-09-27 20:45
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResponseResult responseResult = ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        String jsonStr = JSONUtil.toJsonStr(responseResult);
        WebUtils.renderString(response,jsonStr);

    }
}
