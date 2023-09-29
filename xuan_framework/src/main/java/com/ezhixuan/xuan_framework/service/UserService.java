package com.ezhixuan.xuan_framework.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ezhixuan.xuan_framework.domain.dto.user.UserInfoDTO;
import com.ezhixuan.xuan_framework.domain.entity.User;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.UserInfoVo;

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
    ResponseResult<UserInfoVo> userInfo();

    /**
     * 修改用户信息
     * @param userInfoDTO
     * @return
     */
    ResponseResult<String> updateUserInfo(UserInfoDTO userInfoDTO);
}

