package com.ezhixuan.xuan_framework.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;

public class WebUtils {
  /**
   * 将字符串渲染到客户端
   *
   * @param response 渲染对象
   * @param string 待渲染的字符串
   * @return null
   */
  public static String renderString(HttpServletResponse response, String string) {
    try {
      response.setStatus(200);
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      response.getWriter().print(string);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 设置返回下载返回头
   *
   * @param filename
   * @param response
   * @throws UnsupportedEncodingException
   */
  public static void setDownLoadHeader(String filename, HttpServletResponse response)
      throws UnsupportedEncodingException {
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    response.setCharacterEncoding("utf-8");
    String fname = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment; filename=" + fname);
  }
}
