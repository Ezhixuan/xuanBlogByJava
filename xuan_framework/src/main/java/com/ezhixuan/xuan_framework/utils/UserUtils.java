package com.ezhixuan.xuan_framework.utils;

import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.domain.entity.LoginUser;
import com.ezhixuan.xuan_framework.domain.entity.User;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

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
   *
   * @return LoginUser
   */
  public static LoginUser getLoginUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (ObjectUtils.isEmpty(authentication)) {
      throw new BaseException(AppHttpCodeEnum.LOGIN_USER_ERROR);
    }
    Object principal = authentication.getPrincipal();
    if (principal == "anonymousUser") {
      // 匿名用户 在匿名评论时会被使用
      return new LoginUser(createUser());
    }
    return (LoginUser) principal;
  }

  /**
   * 获取当前登录用户
   *
   * @return User
   */
  public static User getUser() {
    return getLoginUser().getUser();
  }

  /**
   * 是否是管理员
   *
   * @return boolean
   */
  public static boolean isSystem() {
    User user = getUser();
    return CommonConstant.SYSTEM.equals(user.getType());
  }

  /**
   * 是否是超级管理员
   *
   * @return
   */
  public static boolean isAdmin() {
    return getLoginUser().getRoles().contains(CommonConstant.ADMIN);
  }

  /**
   * 用户不存在时使用，创建一个新用户 id默认为-1 私有
   *
   * @return
   */
  private static User createUser() {
    User user = new User();
    user.setId(-1L);
    return user;
  }
}
