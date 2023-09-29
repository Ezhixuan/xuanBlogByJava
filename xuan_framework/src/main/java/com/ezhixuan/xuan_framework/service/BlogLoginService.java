package com.ezhixuan.xuan_framework.service;

import com.ezhixuan.xuan_framework.domain.dto.user.UserLoginDTO;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.user.BlogUserLoginVo;

public interface BlogLoginService {
    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    ResponseResult<BlogUserLoginVo> login(UserLoginDTO userLoginDTO);

    /**
     * 登出
     * @return
     */
    ResponseResult<String> logout();

}
