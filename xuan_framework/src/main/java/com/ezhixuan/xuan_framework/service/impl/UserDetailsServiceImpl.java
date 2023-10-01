package com.ezhixuan.xuan_framework.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.dao.MenuDao;
import com.ezhixuan.xuan_framework.dao.RoleDao;
import com.ezhixuan.xuan_framework.dao.UserDao;
import com.ezhixuan.xuan_framework.domain.entity.LoginUser;
import com.ezhixuan.xuan_framework.domain.entity.User;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.exception.UserLoginException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @program: xuanBlog
 * @description: 返回登录用户信息
 * @author: Mr.Xuan
 * @create: 2023-09-27 17:59
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Resource private UserDao userDao;

  @Resource private MenuDao menuDao;

  @Resource private RoleDao roleDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 1. 查询用户信息
    User user = userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, username));
    if (ObjectUtil.isNull(user)) {
      throw new UserLoginException(AppHttpCodeEnum.LOGIN_USER_ERROR.getCode(), "用户不存在");
    }
    if (CommonConstant.USER_TYPE_ADMIN.equals(user.getType())) {
      // 2. 查询用户权限信息
      List<String> perms = menuDao.queryPermsByUserId(user.getId());
      // 3. 查询用户角色信息
      List<String> roles = roleDao.queryUserRole(user.getId());
      // 3. 返回UserDetails
      return new LoginUser(user, perms, roles);
    }
    return new LoginUser(user);
  }
}
