package com.ezhixuan.xuan_framework.utils;

import com.ezhixuan.xuan_framework.domain.entity.LoginUser;
import com.ezhixuan.xuan_framework.domain.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @program: xuanBlog
 * @description: 获取用户信息
 * @author: Mr.Xuan
 * @create: 2023-09-28 18:33
 */
public class UserUtils {

  private static final LoginUser LOGIN_USER =
      (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  
  public static User getUser(){
    return LOGIN_USER.getUser();
  }
}
