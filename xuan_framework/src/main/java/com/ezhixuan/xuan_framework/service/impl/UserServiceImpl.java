package com.ezhixuan.xuan_framework.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.RedisKeyConstant;
import com.ezhixuan.xuan_framework.dao.UserDao;
import com.ezhixuan.xuan_framework.domain.dto.user.UserInfoDTO;
import com.ezhixuan.xuan_framework.domain.entity.LoginUser;
import com.ezhixuan.xuan_framework.domain.entity.User;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.UserInfoVo;
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.service.UserService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import com.ezhixuan.xuan_framework.utils.UserUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户表(User)表服务实现类
 *
 * @author Ezhixuan
 * @since 2023-09-28 23:20:10
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

  @Resource private RedisTemplate<String, Object> redisTemplate;

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
        .set(RedisKeyConstant.BLOG_LOGIN_USER + user.getId(), JSONUtil.toJsonStr(loginUser));
    // 5. 返回
    return ResponseResult.SUCCESS;
  }
}
