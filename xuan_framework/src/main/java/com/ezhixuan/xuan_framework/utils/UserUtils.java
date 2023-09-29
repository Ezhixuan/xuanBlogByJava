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
  
  public User getUser(){
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal == "anonymousUser"){
      return new User();
    }
    LoginUser loginUser = (LoginUser) principal;
    return loginUser.getUser();
  }
}
