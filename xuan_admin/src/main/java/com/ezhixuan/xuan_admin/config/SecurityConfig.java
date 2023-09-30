package com.ezhixuan.xuan_admin.config;

import com.ezhixuan.xuan_admin.filter.JWTAuthenticationTokenFilter;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @program: xuanBlog
 * @description: SpringSecurity配置文件
 * @author: Mr.Xuan
 * @create: 2023-09-27 19:08
 */
@Configuration
public class SecurityConfig {

  @Resource private JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter;

  @Resource private AuthenticationEntryPoint authenticationEntryPoint;
  @Resource private AccessDeniedHandler accessDeniedHandler;

  /**
   * 获取认证管理器
   *
   * @param authenticationConfiguration
   * @return
   * @throws Exception
   */
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  /**
   * 密码加密
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 过滤器链配置
   *
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http // 关闭csrf
        .csrf()
        .disable()
        // 不通过Session获取SecurityContext
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        // 对于登录接口 允许匿名访问
        .antMatchers("/user/login")
        .anonymous()
        .antMatchers("/doc.html/**")
        .anonymous()
        //        // 对于登出接口 需要鉴权访问
        //        .antMatchers("/logout")
        //        .authenticated()
        //        // 对于用户信息接口 需要鉴权访问
        //        .antMatchers("user/userInfo")
        //        .authenticated()
        // 除上面外的所有请求全部需要鉴权访问
        .anyRequest()
        .authenticated();
    // 添加允许跨域
    http.cors();
    // 添加JWT过滤器
    http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    // 添加自定义处理器
    //    http.formLogin()
    //        .successHandler(authenticationSuccessHandler)
    //        .failureHandler(authenticationFailureHandler);
    http.exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .accessDeniedHandler(accessDeniedHandler);
    http.logout().disable();
    return http.build();
  }
}
