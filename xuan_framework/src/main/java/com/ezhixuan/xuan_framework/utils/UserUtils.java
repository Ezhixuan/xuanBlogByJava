package com.ezhixuan.xuan_framework.utils;

import com.ezhixuan.xuan_framework.constant.CommonConstant;
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
  /**
   * 获取LoginUser
   * @return
   */
  public static LoginUser getLoginUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return null;
    }
    Object principal = authentication.getPrincipal();
    if (principal == "anonymousUser") {
      return null;
    }
    return (LoginUser) principal;
  }

  /**
   * 获取当前登录用户
   *
   * @return
   */
  public static User getUser() {
    LoginUser loginUser = getLoginUser();
    if (loginUser == null){
      return createUser();
    }
    return loginUser.getUser();
  }

  /**
   * 是否是超级管理员
   * @return
   */
  public static boolean isSystem(){
    User user = getUser();
    if (user == null){
      return false;
    }
    return "1".equals(user.getType());
  }

  /**
   * 是否是超级管理员
   * @return
   */
  public static boolean isAdmin(){
    LoginUser loginUser = getLoginUser();
    if (loginUser == null){
      return false;
    }
    return loginUser.getRoles().contains(CommonConstant.ADMIN);
  }

  /**
   * 用户不存在时使用，创建一个新用户 id默认为-1
   * 私有
   * @return
   */
  private static User createUser() {
    User user = new User();
    user.setId(-1L);
    return user;
  }
}
