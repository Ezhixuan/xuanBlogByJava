package com.ezhixuan.xuan_framework.service;

import com.ezhixuan.xuan_framework.domain.dto.login.UserLoginDTO;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;

public interface BlogLoginService {
    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    ResponseResult login(UserLoginDTO userLoginDTO);

    /**
     * 登出
     * @return
     */
    ResponseResult logout();

}
