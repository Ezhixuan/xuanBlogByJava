package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.dao.UserRoleDao;
import com.ezhixuan.xuan_framework.domain.entity.UserRole;
import com.ezhixuan.xuan_framework.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author Ezhixuan
 * @since 2023-10-03 17:28:21
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

}

