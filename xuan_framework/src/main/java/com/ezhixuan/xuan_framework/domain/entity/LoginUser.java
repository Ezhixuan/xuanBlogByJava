package com.ezhixuan.xuan_framework.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @program: xuanBlog
 * @description: 登录用户
 * @author: Mr.Xuan
 * @create: 2023-09-27 18:13
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    value = {
      "authorities",
      "password",
      "accountNonExpired",
      "accountNonLocked",
      "credentialsNonExpired",
      "enabled",
      "username"
    })
public class LoginUser implements UserDetails {
  /** 用户信息 */
  private User user;

  /** 权限信息 */
  private List<String> permissions;

  /** 角色信息 */
  private List<String> roles;

  /** 存储SpringSecurity所需权限集合 */
  private List<SimpleGrantedAuthority> authorities;

  public LoginUser(User user) {
    this.user = user;
    this.permissions = null;
    this.roles = null;
  }

  public LoginUser(User user, List<String> permissions) {
    this.user = user;
    this.permissions = permissions;
    this.roles = null;
  }

  public LoginUser(User user, List<String> permissions, List<String> roles) {
    this.user = user;
    this.permissions = permissions;
    this.roles = roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (authorities != null) {
      return authorities;
    }
    // 把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
    authorities =
        permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
