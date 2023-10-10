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
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.service.LoginService;
import com.ezhixuan.xuan_framework.service.MenuService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import com.ezhixuan.xuan_framework.utils.JwtUtil;
import com.ezhixuan.xuan_framework.utils.RedisUtil;
import com.ezhixuan.xuan_framework.utils.UserUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
  @Resource private MenuService menuService;

  @Override
  public ResponseResult<Map<String, String>> userLoginSys(UserLoginDTO userLoginDTO) {
    // 用户认证
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(
            userLoginDTO.getUserName(), userLoginDTO.getPassword());
    Authentication authenticate = authenticationManager.authenticate(token);
    if (ObjectUtil.isNull(authenticate)) {
      throw new BaseException(AppHttpCodeEnum.LOGIN_FAILURE.getCode(), "用户名或密码错误");
    }
    // 获取用户id，生成jwt
    LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
    String userId = loginUser.getUser().getId().toString();
    String jwt = JwtUtil.createJWT(userId);
    // 将用户信息存入redis中
    redisUtil.setValue(RedisKeyConstant.BLOG_LOGIN_USER_BY_ID + userId, loginUser);
    // 返回jwt
    Map<String, String> map = new HashMap<>();
    map.put("token", jwt);
    return ResponseResult.okResult(map);
  }

  /**
   * 退出登录
   * @return 处理结果
   */
  @Override
  public ResponseResult<String> userLogoutSys() {
    Long userId = UserUtils.getUser().getId();
    // 到redis中删除对应user数据
    redisUtil.cleanCache(RedisKeyConstant.BLOG_LOGIN_USER_BY_ID + userId);
    return ResponseResult.SUCCESS;
  }

  /**
   * 获取用户信息
   *
   * @return
   */
  @Override
  public ResponseResult<SysUserInfo> selectUserInfoSys() {
    LoginUser loginUser = UserUtils.getLoginUser();
    List<String> permissions = loginUser.getPermissions();
    if (UserUtils.isAdmin()){
      // 当前用户为超级管理员
      permissions =menuDao.queryAllChildrenMenu();
    }
    User user = loginUser.getUser();
    UserInfoVo userInfoVo = BeanUtil.copyBean(user, UserInfoVo.class);
    // 封装返回
    SysUserInfo userInfo =
        SysUserInfo.builder().permissions(permissions).roles(loginUser.getRoles()).user(userInfoVo).build();
    return ResponseResult.okResult(userInfo);
  }

  /**
   * 获取路由信息
   *
   * @return
   */
  @Override
  public ResponseResult<RoutersVo> selectUserRoutersSys() {
    LoginUser loginUser = UserUtils.getLoginUser();
    List<String> userRole = loginUser.getRoles();
    List<Menu> menus = null;
    if (userRole.contains(CommonConstant.ADMIN)) {
      menus = menuDao.queryAllRootMenu();
    } else {
      menus = menuDao.queryRootMenuByUserId(loginUser.getUser().getId());
    }
    // 构建树状结构
    menus = menuService.buildTree(menus);
    // 封装返回
    RoutersVo routersVo = new RoutersVo();
    routersVo.setMenus(menus);
    return ResponseResult.okResult(routersVo);
  }

  
}
