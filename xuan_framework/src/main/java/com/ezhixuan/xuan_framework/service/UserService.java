package com.ezhixuan.xuan_framework.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ezhixuan.xuan_framework.domain.dto.user.UserInfoDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserPageDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserRegisterDTO;
import com.ezhixuan.xuan_framework.domain.dto.user.UserDTO;
import com.ezhixuan.xuan_framework.domain.entity.User;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.UserInfoVo;
import com.ezhixuan.xuan_framework.domain.vo.user.UserQueryVo;

import java.util.List;

/**
 * 用户表(User)表服务接口
 *
 * @author Ezhixuan
 * @since 2023-09-28 23:20:10
 */
public interface UserService extends IService<User> {

    /**
     * 用户信息
     * @return
     */
    ResponseResult<UserInfoVo> selectUser();

    /**
     * 修改用户信息
     * @param userInfoDTO
     * @return
     */
    ResponseResult<String> updateUser(UserInfoDTO userInfoDTO);

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    ResponseResult<String> insertUser(UserRegisterDTO userRegisterDTO);

    /**
     * 用户列表
     * @param userDto
     * @return
     */
    ResponseResult<PageVo> selectUserPageSys(UserPageDTO userDto);

    /**
     * 新增用户
     * @param userDto
     * @return
     */
    ResponseResult<String> insertUserSys(UserDTO userDto);

    /**
     * 删除用户
     * @param ids
     * @return
     */
    ResponseResult<String> deleteUserByIdSys(List<Long> ids);

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    ResponseResult<UserQueryVo> selectUserByIdSys(Long id);

    /**
     * 修改用户信息
     * @param userDto
     * @return
     */
    ResponseResult<String> updateUserSys(UserDTO userDto);
}

