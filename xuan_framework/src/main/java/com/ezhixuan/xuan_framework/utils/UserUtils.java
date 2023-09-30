package com.ezhixuan.xuan_framework.utils;

import com.ezhixuan.xuan_framework.domain.entity.LoginUser;
import com.ezhixuan.xuan_framework.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @program: xuanBlog
 * @description: 获取用户信息
 * @author: Mr.Xuan
 * @create: 2023-09-28 18:33
 */
@Slf4j
public class UserUtils {
  public User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return createUser();
    }
    Object principal = authentication.getPrincipal();
    if (principal == "anonymousUser") {
      return createUser();
    }
    LoginUser loginUser = (LoginUser) principal;
    return loginUser.getUser();
  }

  private User createUser() {
    User user = new User();
    user.setId(-1L);
    return user;
  }
}
