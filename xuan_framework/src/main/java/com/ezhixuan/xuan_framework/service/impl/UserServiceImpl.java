package com.ezhixuan.xuan_framework.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.constant.CommonConstant;
import com.ezhixuan.xuan_framework.constant.RedisKeyConstant;
import com.ezhixuan.xuan_framework.dao.UserDao;
import com.ezhixuan.xuan_framework.domain.dto.user.UserInfoDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserPageDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserRegisterDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserSaveDTO;
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
import com.ezhixuan.xuan_framework.utils.UserUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
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

  @Resource private RedisTemplate<String, Object> redisTemplate;

  @Resource private PasswordEncoder passwordEncoder;

  @Resource private UserRoleService userRoleService;

  @Resource private RoleService roleService;

  /**
   * 用户信息
   *
   * @return
   */
  @Override
  public ResponseResult<UserInfoVo> userInfo() {
    // 1. 获取用户信息
    User user = UserUtils.getUser();
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
    if (ObjectUtils.isEmpty(userRegisterDTO)) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    if (StrUtil.isBlank(userRegisterDTO.getPassword())) {
      throw new BaseException("密码不能为空");
    }
    if (StrUtil.isBlank(userRegisterDTO.getNickName())) {
      throw new BaseException("昵称不能为空");
    }
    if (StrUtil.isBlank(userRegisterDTO.getEmail())) {
      throw new BaseException("邮箱不能为空");
    }
    if (StrUtil.isBlank(userRegisterDTO.getUserName())) {
      throw new BaseException("用户名不能为空");
    }
    // 2. 对数据是否重复进行判断
    if (exist("user_name", userRegisterDTO.getUserName())) {
      throw new BaseException("用户名已存在");
    }
    if (exist("nick_name", userRegisterDTO.getNickName())) {
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
   *
   * @param column
   * @param val
   * @return
   */
  private boolean exist(String column, String val) {
    int count = count(Wrappers.<User>query().eq(column, val));
    return count > 0;
  }

  /**
   * 用户列表
   *
   * @param userDto
   * @return
   */
  @Override
  public ResponseResult<PageVo> queryList(UserPageDTO userDto) {
    // 1. 校验参数
    userDto.check();
    // 2. 构建查询
    LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
    // 2.1 如果用户名不为空，则模糊查询
    userLambdaQueryWrapper.like(
        StringUtils.hasText(userDto.getUserName()), User::getUserName, userDto.getUserName());
    // 2.2 如果手机号不为空，按手机号精确查询
    userLambdaQueryWrapper.eq(
        StringUtils.hasText(userDto.getPhonenumber()),
        User::getPhonenumber,
        userDto.getPhonenumber());
    // 2.3 如果用户状态不为空，则按用户状态查询，否则查询正常状态的用户
    if (StringUtils.hasText(userDto.getStatus())) {
      userLambdaQueryWrapper.eq(User::getStatus, userDto.getStatus());
    } else {
      userLambdaQueryWrapper.eq(User::getStatus, CommonConstant.STATUS_NORMAL);
    }
    // 2.4 根据用户创建时间排序
    userLambdaQueryWrapper.orderByDesc(User::getCreateTime);
    // 3. 分页查询
    Page<User> userPage = new Page<>(userDto.getPageNum(), userDto.getPageSize());
    page(userPage, userLambdaQueryWrapper);
    // 4. 封装返回
    List<UserVo> userVos = BeanUtil.copyBeanList(userPage.getRecords(), UserVo.class);
    PageVo pageVo = new PageVo(userVos, userPage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 新增用户
   *
   * @param userDto
   * @return
   */
  @Override
  @Transactional
  public ResponseResult<String> saveByDto(UserSaveDTO userDto) {
    // 1. 校验参数
    if (userDto == null) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    } else if (!StringUtils.hasText(userDto.getUserName())) {
      throw new BaseException("必需填写用户名");
    } else if (!StringUtils.hasText(userDto.getEmail())) {
      throw new BaseException("必需填写邮箱");
    } else if (!StringUtils.hasText(userDto.getNickName())) {
      throw new BaseException("必需填写昵称");
    } else if (!StringUtils.hasText(userDto.getPhonenumber())) {
      throw new BaseException("必需填写手机号");
    } else if (!StringUtils.hasText(userDto.getPassword())) {
      throw new BaseException("必需填写密码");
    }
    // 2. 判断是否重复
    // 2.1 判断用户名是否重复
    if (exist("user_name", userDto.getUserName())) {
      throw new BaseException("用户名已存在");
    }
    // 2.2 判断邮箱是否重复
    if (exist("email", userDto.getEmail())) {
      throw new BaseException("邮箱已存在");
    }
    // 2.3 判断手机号是否重复
    if (exist("phonenumber", userDto.getPhonenumber())) {
      throw new BaseException("手机号已存在");
    }
    // 3. 对密码进行加密
    String password = passwordEncoder.encode(userDto.getPassword());
    userDto.setPassword(password);
    // 4. 保存
    User user = BeanUtil.copyBean(userDto, User.class);
    save(user);
    // 5. 建立用户与角色的关联
    if (!ObjectUtils.isEmpty(userDto.getRoleIds())) {
      userDto
          .getRoleIds()
          .forEach(
              roleId -> {
                userRoleService.save(new UserRole(user.getId(), roleId));
              });
    }
    // 6. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 删除用户
   *
   * @param ids
   * @return
   */
  @Override
  @Transactional
  public ResponseResult<String> delete(List<Long> ids) {
    // 1. 校验参数
    if (ObjectUtils.isEmpty(ids)) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 删除用户和角色的关系
    ids.forEach(
        id -> {
          if (id.equals(UserUtils.getUser().getId())) {
            throw new BaseException("不能删除自己");
          }
          userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id));
        });
    // 3. 删除用户
    removeByIds(ids);
    // 4. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 获取用户信息
   *
   * @param id
   * @return
   */
  @Override
  public ResponseResult<UserQueryVo> query(Long id) {
    // 1. 校验参数
    if (id == null) {
      throw new BaseException("用户id不能为空");
    }
    // 2. 查询
    User user = getById(id);
    if (user == null) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 3. 查询所有角色信息
    List<Role> roles = roleService.list();
    // 4. 查询用户的角色信息
    List<Long> roleIds =
        userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id)).stream()
            .map(UserRole::getRoleId)
            .collect(Collectors.toList());
    // 5. 封装返回
    UserQueryVo userQueryVo =
        UserQueryVo.builder().roleIds(roleIds).roles(roles).user(user).build();
    return ResponseResult.okResult(userQueryVo);
  }

  /**
   * 修改用户信息
   *
   * @param userDto
   * @return
   */
  @Override
  @Transactional
  public ResponseResult<String> updateByDto(UserSaveDTO userDto) {
    // 1. 校验参数
    if (userDto == null || ObjectUtils.isEmpty(userDto.getId())) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 判断是否重复
    User byId = getById(userDto.getId());
    // 2.1 判断用户名是否重复
    if (StringUtils.hasText(userDto.getUserName())
        && !userDto.getUserName().equals(byId.getUserName())
        && exist("user_name", userDto.getUserName())) {
      throw new BaseException("用户名已存在");
    }
    // 2.2 判断邮箱是否重复
    if (StringUtils.hasText(userDto.getEmail())
        && !userDto.getEmail().equals(byId.getEmail())
        && exist("email", userDto.getEmail())) {
      throw new BaseException("邮箱已存在");
    }
    // 2.3 判断手机号是否重复
    if (StringUtils.hasText(userDto.getPhonenumber())
        && !userDto.getPhonenumber().equals(byId.getPhonenumber())
        && exist("phonenumber", userDto.getPhonenumber())) {
      throw new BaseException("手机号已存在");
    }
    // 3. 查询是否有对用户角色表进行修改
    if (!ObjectUtils.isEmpty(userDto.getRoleIds())) {
      // 3.1 删除原有的用户角色关系
      userRoleService.remove(
          Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userDto.getId()));
      // 3.2 建立新的关系
      userDto
          .getRoleIds()
          .forEach(roleId -> userRoleService.save(new UserRole(userDto.getId(), roleId)));
    }
    // 4. 更新
    updateById(userDto);
    // 5. 返回
    return ResponseResult.SUCCESS;
  }
}
