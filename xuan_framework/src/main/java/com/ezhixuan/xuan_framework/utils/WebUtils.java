package com.ezhixuan.xuan_framework.utils;

import cn.hutool.json.JSONUtil;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

public class WebUtils
{
    /**
     * 将字符串渲染到客户端
     * 
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回json
     * @param response
     * @param appHttpCodeEnum
     */
    public static void sendJsonResponse(HttpServletResponse response, AppHttpCodeEnum appHttpCodeEnum){
        ResponseResult responseResult = ResponseResult.errorResult(appHttpCodeEnum);
        String jsonStr = JSONUtil.toJsonStr(responseResult);
        WebUtils.renderString(response,jsonStr);
    }
}