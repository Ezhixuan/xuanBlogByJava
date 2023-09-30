package com.ezhixuan.xuan_framework.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.RedisKeyConstant;
import com.ezhixuan.xuan_framework.dao.UserDao;
import com.ezhixuan.xuan_framework.domain.dto.user.UserInfoDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserRegisterDTO;
import com.ezhixuan.xuan_framework.domain.entity.LoginUser;
import com.ezhixuan.xuan_framework.domain.entity.User;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.UserInfoVo;
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.service.UserService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import com.ezhixuan.xuan_framework.utils.UserUtils;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 用户表(User)表服务实现类
 *
 * @author Ezhixuan
 * @since 2023-09-28 23:20:10
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

  @Resource private RedisTemplate<String, Object> redisTemplate;

  @Resource private PasswordEncoder passwordEncoder;

  /**
   * 用户信息
   *
   * @return
   */
  @Override
  public ResponseResult<UserInfoVo> userInfo() {
    // 1. 获取用户信息
    UserUtils userUtils = new UserUtils();
    User user = userUtils.getUser();
    // 2. 封装返回
    UserInfoVo userInfoVo = BeanUtil.copyBean(user, UserInfoVo.class);
    return ResponseResult.okResult(userInfoVo);
  }

  /**
   * 修改用户信息
   *
   * @param userInfoDTO
   * @return
   */
  @Override
  public ResponseResult<String> updateUserInfo(UserInfoDTO userInfoDTO) {
    // 1. 校验参数
    if (userInfoDTO == null || userInfoDTO.getId() == null) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 封装
    User user = BeanUtil.copyBean(userInfoDTO, User.class);
    // 3. 保存
    updateById(user);
    // 4. 修改redis缓存的数据
    user = getById(user.getId());
    LoginUser loginUser =
        (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    loginUser.setUser(user);
    redisTemplate
        .opsForValue()
        .set(RedisKeyConstant.BLOG_LOGIN_USER_BY_ID + user.getId(), JSONUtil.toJsonStr(loginUser));
    // 5. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 用户注册
   *
   * @param userRegisterDTO
   * @return
   */
  @Override
  public ResponseResult<String> register(UserRegisterDTO userRegisterDTO) {
    // 1. 数据校验
    if (ObjectUtils.isEmpty(userRegisterDTO)){
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    if (StrUtil.isBlank(userRegisterDTO.getPassword())){
      throw new BaseException("密码不能为空");
    }
    if (StrUtil.isBlank(userRegisterDTO.getNickName())){
      throw new BaseException("昵称不能为空");
    }
    if (StrUtil.isBlank(userRegisterDTO.getEmail())){
      throw new BaseException("邮箱不能为空");
    }
    if (StrUtil.isBlank(userRegisterDTO.getUserName())){
      throw new BaseException("用户名不能为空");
    }
    // 2. 对数据是否重复进行判断
    if (exist("user_name",userRegisterDTO.getUserName())){
      throw new BaseException("用户名已存在");
    }
    if (exist("nick_name",userRegisterDTO.getNickName())){
      throw new BaseException("昵称已存在");
    }
    // 3. 对用户密码进行加密
    String password = passwordEncoder.encode(userRegisterDTO.getPassword());
    userRegisterDTO.setPassword(password);
    // 4. 封装
    User user = BeanUtil.copyBean(userRegisterDTO, User.class);
    // 5. 保存
    save(user);
    // 6. 返回
    return ResponseResult.SUCCESS;

  }

  /**
   * 判断user中某字段的某值是否存在
   * @param column
   * @param val
   * @return
   */
  private boolean exist(String column,String val) {
    int count = count(Wrappers.<User>query().eq(column, val));
    return count > 0;
  }
  
}
