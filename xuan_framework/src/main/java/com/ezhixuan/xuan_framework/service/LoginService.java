package com.ezhixuan.xuan_framework.service;

import com.ezhixuan.xuan_framework.domain.dto.user.UserLoginDTO;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.SysUserInfo;

import java.util.Map;

public interface LoginService {
    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    ResponseResult<Map<String, String>> login(UserLoginDTO userLoginDTO);

    /**
     * 登出
     * @return
     */
    ResponseResult<String> logout();

    /**
     * 获取用户信息
     *
     * @return
     */
    ResponseResult<SysUserInfo> getInfo();

}
