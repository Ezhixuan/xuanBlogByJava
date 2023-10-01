package com.ezhixuan.xuan_framework.service.impl;

import com.ezhixuan.xuan_framework.service.PermissionService;
import com.ezhixuan.xuan_framework.utils.UserUtils;
import org.springframework.stereotype.Service;

/**
 * @program: xuanBlog
 * @description: PermissionService实现类
 * @author: Mr.Xuan
 * @create: 2023-10-01 21:45
 */
@Service("ps")
public class PermissionServiceImpl implements PermissionService{
    @Override
    public boolean hasPermission(String permission) {
        // 1. 管理员具有所有权限
        if (UserUtils.isAdmin()){
            return true;
        }
        // 2. 查询用户当前权限列表，判断是否存在该permission
        return UserUtils.getLoginUser().getRoles().contains(permission);
    }
}
