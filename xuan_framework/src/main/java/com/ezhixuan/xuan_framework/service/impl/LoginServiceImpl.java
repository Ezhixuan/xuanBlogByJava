package com.ezhixuan.xuan_framework.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.constant.RedisKeyConstant;
import com.ezhixuan.xuan_framework.dao.MenuDao;
import com.ezhixuan.xuan_framework.domain.dto.user.UserLoginDTO;
import com.ezhixuan.xuan_framework.domain.entity.LoginUser;
import com.ezhixuan.xuan_framework.domain.entity.Menu;
import com.ezhixuan.xuan_framework.domain.entity.User;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.menu.RoutersVo;
import com.ezhixuan.xuan_framework.domain.vo.user.SysUserInfo;
import com.ezhixuan.xuan_framework.domain.vo.user.UserInfoVo;
import com.ezhixuan.xuan_framework.exception.UserLoginException;
import com.ezhixuan.xuan_framework.service.LoginService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import com.ezhixuan.xuan_framework.utils.JwtUtil;
import com.ezhixuan.xuan_framework.utils.RedisUtil;
import com.ezhixuan.xuan_framework.utils.UserUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @program: xuanBlog
 * @description: 博客登录实现
 * @author: Mr.Xuan
 * @create: 2023-09-27 19:06
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

  @Resource private AuthenticationManager authenticationManager;
  @Resource private MenuDao menuDao;
  @Resource private RedisUtil redisUtil;

  @Override
  public ResponseResult<Map<String, String>> login(UserLoginDTO userLoginDTO) {
    // 1. 用户认证
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(
            userLoginDTO.getUserName(), userLoginDTO.getPassword());
    Authentication authenticate = authenticationManager.authenticate(token);
    if (ObjectUtil.isNull(authenticate)) {
      throw new UserLoginException(AppHttpCodeEnum.LOGIN_FAILURE.getCode(), "用户名或密码错误");
    }
    // 2. 获取用户id，生成jwt
    LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
    String userId = loginUser.getUser().getId().toString();
    String jwt = JwtUtil.createJWT(userId);
    // 3. 将用户信息存入redis中
    redisUtil.setValue(RedisKeyConstant.ADMIN_LOGIN_USER_BY_ID + userId, loginUser);
    // 4. 返回jwt
    Map<String, String> map = new HashMap<>();
    map.put("token", jwt);
    return ResponseResult.okResult(map);
  }

  @Override
  public ResponseResult<String> logout() {
    // 1. 获取userid
    LoginUser loginUser =
        (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // 2. 获取userid
    String userId = loginUser.getUser().getId().toString();
    // 3. 到redis中删除对应user数据
    redisUtil.cleanCache(RedisKeyConstant.ADMIN_LOGIN_USER_BY_ID + userId);
    return ResponseResult.SUCCESS;
  }

  /**
   * 获取用户信息
   *
   * @return
   */
  @Override
  public ResponseResult<SysUserInfo> getInfo() {
    // 1. 获取当前用户
    LoginUser loginUser = UserUtils.getLoginUser();
    if (loginUser == null) {
      throw new UserLoginException(AppHttpCodeEnum.LOGIN_FAILURE.getCode(), "用户未登录");
    }
    // 2. 查询当前用户的角色信息
    List<String> userRole = loginUser.getRoles();
    // 3. 权限，如果用户角色信息包含超级管理员，返回所有权限
    List<String> permissions = loginUser.getPermissions();
    if (userRole.contains(CommonConstant.ADMIN)) {
      permissions = menuDao.queryAllChildrenMenu();
    }
    // 3. 获取用户信息
    User user = loginUser.getUser();
    UserInfoVo userInfoVo = BeanUtil.copyBean(user, UserInfoVo.class);
    // 4. 封装返回
    SysUserInfo userInfo =
        SysUserInfo.builder().permissions(permissions).roles(userRole).user(userInfoVo).build();
    return ResponseResult.okResult(userInfo);
  }

  /**
   * 获取路由信息
   *
   * @return
   */
  @Override
  public ResponseResult<RoutersVo> getRouters() {
    // 1. 获取当前用户
    LoginUser loginUser = UserUtils.getLoginUser();
    Long userId = loginUser.getUser().getId();
    // 2. 查询当前用户的角色信息
    List<String> userRole = loginUser.getRoles();
    List<Menu> menus = null;
    if (userRole.contains(CommonConstant.ADMIN)) {
      menus = menuDao.queryAllRootMenu();
    } else {
      menus = menuDao.queryRootMenuByUserId(loginUser.getUser().getId());
    }
    // 3. 构建根目录
    menus = buildTree(menus);
    // 4. 封装返回
    RoutersVo routersVo = new RoutersVo();
    routersVo.setMenus(menus);
    return ResponseResult.okResult(routersVo);
  }

  /**
   * 构建根目录
   * @param menus
   * @return
   */
  private List<Menu> buildTree(List<Menu> menus) {

    return menus.stream()
        .filter(menu -> menu.getParentId().equals(CommonConstant.ROOT))
        .peek(menu -> menu.setChildren(setChildrenMenu(menus, menu)))
        .collect(Collectors.toList());
  }

  /**
   * 添加children
   * @param menus
   * @param rootMenu
   * @return
   */
  private List<Menu> setChildrenMenu(List<Menu> menus, Menu rootMenu) {
    return menus.stream()
        .filter(menu -> menu.getParentId().equals(rootMenu.getId()))
        .peek(menu -> menu.setChildren(setChildrenMenu(menus, menu)))
        .collect(Collectors.toList());
  }
}
