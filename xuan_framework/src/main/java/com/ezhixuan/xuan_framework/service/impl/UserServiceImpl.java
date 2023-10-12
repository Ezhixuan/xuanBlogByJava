package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.constant.RedisKeyConstant;
import com.ezhixuan.xuan_framework.dao.UserDao;
import com.ezhixuan.xuan_framework.domain.dto.user.UserDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserInfoDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserPageDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserRegisterDTO;
import com.ezhixuan.xuan_framework.domain.entity.LoginUser;
import com.ezhixuan.xuan_framework.domain.entity.Role;
import com.ezhixuan.xuan_framework.domain.entity.User;
import com.ezhixuan.xuan_framework.domain.entity.UserRole;
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.UserInfoVo;
import com.ezhixuan.xuan_framework.domain.vo.user.UserQueryVo;
import com.ezhixuan.xuan_framework.domain.vo.user.UserVo;
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.service.RoleService;
import com.ezhixuan.xuan_framework.service.UserRoleService;
import com.ezhixuan.xuan_framework.service.UserService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import com.ezhixuan.xuan_framework.utils.RedisUtil;
import com.ezhixuan.xuan_framework.utils.UserUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 用户表(User)表服务实现类
 *
 * @author Ezhixuan
 * @since 2023-09-28 23:20:10
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

  @Resource private RedisUtil redisUtil;

  @Resource private PasswordEncoder passwordEncoder;

  @Resource private UserRoleService userRoleService;

  @Resource private RoleService roleService;

  /**
   * 用户信息
   *
   * @return 用户信息
   */
  @Override
  public ResponseResult<UserInfoVo> selectUser() {
    User user = UserUtils.getUser();
    if (CommonConstant.NOT_LOGIN.equals(user.getId())) {
      throw new BaseException(AppHttpCodeEnum.NEED_LOGIN.getCode(), "用户未登录");
    }
    UserInfoVo userInfoVo = BeanUtil.copyBean(user, UserInfoVo.class);
    return ResponseResult.okResult(userInfoVo);
  }

  /**
   * 修改用户信息
   *
   * @param userInfoDTO 用户信息
   * @return 修改结果
   */
  @Override
  public ResponseResult<String> updateUser(UserInfoDTO userInfoDTO) {
    User user = BeanUtil.copyBean(userInfoDTO, User.class);
    updateById(user);
    // 修改redis缓存的数据
    user = getById(user.getId());
    LoginUser loginUser = UserUtils.getLoginUser();
    loginUser.setUser(user);
    redisUtil.setValue(RedisKeyConstant.BLOG_LOGIN_USER_BY_ID + user.getId(), loginUser);
    return ResponseResult.SUCCESS;
  }

  /**
   * 用户注册
   *
   * @param userRegisterDTO 用户注册信息
   * @return 注册结果
   */
  @Override
  public ResponseResult<String> insertUser(UserRegisterDTO userRegisterDTO) {
    // 对数据是否重复进行判断
    if (exist("user_name", userRegisterDTO.getUserName()) != 0) {
      throw new BaseException("用户名已存在");
    }
    if (exist("nick_name", userRegisterDTO.getNickName()) != 0) {
      throw new BaseException("昵称已存在");
    }
    // 对用户密码进行加密
    String password = passwordEncoder.encode(userRegisterDTO.getPassword());
    userRegisterDTO.setPassword(password);
    // 封装
    User user = BeanUtil.copyBean(userRegisterDTO, User.class);
    save(user);
    // 绑定用户角色为普通用户
    userRoleService.save(new UserRole(user.getId(), CommonConstant.ROLE_NORMAL));
    return ResponseResult.SUCCESS;
  }

  /**
   * 判断user中某字段的某值是否存在
   *
   * @param column 字段名
   * @param val 字段值
   * @return 是否存在
   */
  private int exist(String column, String val) {
    return count(Wrappers.<User>query().eq(column, val));
  }

  /**
   * 用户列表
   *
   * @param userDto 用户信息
   * @return 用户列表
   */
  @Override
  public ResponseResult<PageVo> selectUserPageSys(UserPageDTO userDto) {
    userDto.check();
    // 构建查询
    LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userLambdaQueryWrapper.like(
        StringUtils.hasText(userDto.getUserName()), User::getUserName, userDto.getUserName());
    userLambdaQueryWrapper.eq(
        StringUtils.hasText(userDto.getPhonenumber()),
        User::getPhonenumber,
        userDto.getPhonenumber());
    if (StringUtils.hasText(userDto.getStatus())) {
      userLambdaQueryWrapper.eq(User::getStatus, userDto.getStatus());
    } else {
      userLambdaQueryWrapper.eq(User::getStatus, CommonConstant.STATUS_NORMAL);
    }
    userLambdaQueryWrapper.orderByDesc(User::getCreateTime);
    // 分页查询
    Page<User> userPage = new Page<>(userDto.getPageNum(), userDto.getPageSize());
    page(userPage, userLambdaQueryWrapper);
    // 封装返回
    List<UserVo> userVos = BeanUtil.copyBeanList(userPage.getRecords(), UserVo.class);
    PageVo pageVo = new PageVo(userVos, userPage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 新增用户
   *
   * @param userDto 用户信息
   * @return 结果
   */
  @Override
  @Transactional
  public ResponseResult<String> insertUserSys(UserDTO userDto) {
    if (exist("user_name", userDto.getUserName()) != 0) {
      throw new BaseException("用户名已存在");
    }
    // 判断邮箱是否重复
    if (exist("email", userDto.getEmail()) != 0) {
      throw new BaseException("邮箱已存在");
    }
    // 判断手机号是否重复
    if (exist("phonenumber", userDto.getPhonenumber()) != 0) {
      throw new BaseException("手机号已存在");
    }
    // 对密码进行加密
    String password = passwordEncoder.encode(userDto.getPassword());
    userDto.setPassword(password);
    User user = BeanUtil.copyBean(userDto, User.class);
    save(user);
    // 建立用户与角色的关联
    if (!ObjectUtils.isEmpty(userDto.getRoleIds())) {
      userDto
          .getRoleIds()
          .forEach(
              roleId -> {
                userRoleService.save(new UserRole(user.getId(), roleId));
              });
    }
    // 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 删除用户
   *
   * @param ids 用户id
   * @return 结果
   */
  @Override
  @Transactional
  public ResponseResult<String> deleteUserByIdSys(List<Long> ids) {
    // 删除用户和角色的关系
    ids.forEach(
        id -> {
          if (id.equals(UserUtils.getUser().getId())) {
            throw new BaseException("不能删除自己");
          }
          userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id));
        });
    // 删除用户
    removeByIds(ids);
    // 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 获取用户信息
   *
   * @param id 用户id
   * @return 用户信息
   */
  @Override
  public ResponseResult<UserQueryVo> selectUserByIdSys(Long id) {
    // 查询
    User user = getById(id);
    // 查询所有角色信息
    List<Role> roles = roleService.list();
    // 查询用户的角色信息
    List<Long> roleIds =
        userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id)).stream()
            .map(UserRole::getRoleId)
            .collect(Collectors.toList());
    // 封装返回
    UserQueryVo userQueryVo =
        UserQueryVo.builder().roleIds(roleIds).roles(roles).user(user).build();
    return ResponseResult.okResult(userQueryVo);
  }

  /**
   * 修改用户信息
   *
   * @param userDto 用户信息
   * @return 结果
   */
  @Override
  @Transactional
  public ResponseResult<String> updateUserSys(UserDTO userDto) {
    // 判断用户名是否重复
    if (exist("user_name", userDto.getUserName()) > 1) {
      throw new BaseException("用户名已存在");
    }
    // 判断邮箱是否重复
    if (exist("email", userDto.getEmail()) > 1) {
      throw new BaseException("邮箱已存在");
    }
    // 判断手机号是否重复
    if (exist("phonenumber", userDto.getPhonenumber()) > 1) {
      throw new BaseException("手机号已存在");
    }
    // 查询是否有对用户角色表进行修改
    if (!ObjectUtils.isEmpty(userDto.getRoleIds())) {
      userRoleService.remove(
          Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userDto.getId()));
      userDto
          .getRoleIds()
          .forEach(roleId -> userRoleService.save(new UserRole(userDto.getId(), roleId)));
    }
    User user = BeanUtil.copyBean(userDto, User.class);
    updateById(user);
    // 返回
    return ResponseResult.SUCCESS;
  }
}
