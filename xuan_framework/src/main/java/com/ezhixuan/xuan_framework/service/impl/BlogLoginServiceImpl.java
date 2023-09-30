package com.ezhixuan.xuan_framework.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.ezhixuan.xuan_framework.constant.RedisKeyConstant;
import com.ezhixuan.xuan_framework.domain.dto.user.UserLoginDTO;
import com.ezhixuan.xuan_framework.domain.entity.LoginUser;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.BlogUserLoginVo;
import com.ezhixuan.xuan_framework.domain.vo.user.UserInfoVo;
import com.ezhixuan.xuan_framework.exception.UserLoginException;
import com.ezhixuan.xuan_framework.service.BlogLoginService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import com.ezhixuan.xuan_framework.utils.JwtUtil;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
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
@Service("blogLoginService")
public class BlogLoginServiceImpl implements BlogLoginService {

  @Resource private AuthenticationManager authenticationManager;
  
  @Resource private RedisTemplate<String, Object> redisTemplate;

  @Override
  public ResponseResult<BlogUserLoginVo> login(UserLoginDTO userLoginDTO) {
    // 1. 用户认证
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLoginDTO.getUserName(), userLoginDTO.getPassword());
    Authentication authenticate = authenticationManager.authenticate(token);
    if (ObjectUtil.isNull(authenticate)){
      throw new UserLoginException(AppHttpCodeEnum.LOGIN_FAILURE.getCode(),"用户名或密码错误");
    }
    // 2. 获取用户id，生成jwt
    LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
    String userId = loginUser.getUser().getId().toString();
    String jwt = JwtUtil.createJWT(userId);
    // 3. 将用户信息存入redis中
    redisTemplate.opsForValue().set(RedisKeyConstant.BLOG_LOGIN_USER_BY_ID + userId, JSONUtil.toJsonStr(loginUser));
    // 4. 返回jwt和userInfo
    UserInfoVo userInfoVo = BeanUtil.copyBean(loginUser.getUser(), UserInfoVo.class);
    BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
    return ResponseResult.okResult(blogUserLoginVo);
  }

  @Override
  public ResponseResult<String> logout() {
    // 1. 获取userid
    LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // 2. 获取userid
    String userId = loginUser.getUser().getId().toString();
    // 3. 到redis中删除对应user数据
    redisTemplate.delete(RedisKeyConstant.BLOG_LOGIN_USER_BY_ID + userId);
    return ResponseResult.SUCCESS;
  }
}
