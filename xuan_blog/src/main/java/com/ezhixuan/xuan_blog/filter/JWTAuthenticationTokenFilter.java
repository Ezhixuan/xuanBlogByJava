package com.ezhixuan.xuan_blog.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ezhixuan.xuan_framework.constant.RedisKeyConstant;
import com.ezhixuan.xuan_framework.domain.entity.LoginUser;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.utils.JwtUtil;
import com.ezhixuan.xuan_framework.utils.WebUtils;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @program: xuanBlog
 * @description: JWT认证过滤器
 * @author: Mr.Xuan
 * @create: 2023-09-27 19:53
 */
@Slf4j
@Component("jwtAuthenticationTokenFilter")
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

  @Resource private RedisTemplate<String, Object> redisTemplate;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    log.info("JWTAuthenticationTokenFilter doFilterInternal ....");
    // 1. 从Request中获取jwt
    String token = request.getHeader("token");
    if (StrUtil.isBlank(token)) {
      // token 不存在
      filterChain.doFilter(request, response);
      return;
    }
    // 2. 解析token
    Claims claims;
    try {
      claims = JwtUtil.parseJWT(token);

    } catch (Exception e) {
      e.printStackTrace();
      ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
      String jsonStr = JSONUtil.toJsonStr(responseResult);
      WebUtils.renderString(response,jsonStr);
      return;
    }
    // 3. 从redis中获取用户信息
    String userId = claims.getSubject();
    Object JsonStr = redisTemplate.opsForValue().get(RedisKeyConstant.BLOG_LOGIN_USER_BY_ID + userId);
    LoginUser loginUser = JSONUtil.toBean((String) JsonStr, LoginUser.class);
    
    if (ObjectUtil.isNull(loginUser)) {
      ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_USER_ERROR);
      String jsonStr = JSONUtil.toJsonStr(responseResult);
      WebUtils.renderString(response,jsonStr);
    }
    // 4. 将用户信息存入SecurityContext
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    // 5. 放行
    filterChain.doFilter(request, response);
  }
  
}
