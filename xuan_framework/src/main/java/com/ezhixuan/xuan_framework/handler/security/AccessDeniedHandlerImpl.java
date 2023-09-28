package com.ezhixuan.xuan_framework.handler.security;

import cn.hutool.json.JSONUtil;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: xuanBlog
 * @description: 授权失败异常处理器
 * @author: Mr.Xuan
 * @create: 2023-09-27 20:56
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        ResponseResult responseResult = ResponseResult.okResult(AppHttpCodeEnum.LOGIN_DENIED_FAILURE);
        String jsonStr = JSONUtil.toJsonStr(responseResult);
        WebUtils.renderString(response,jsonStr);

    }
}
