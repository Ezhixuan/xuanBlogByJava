package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.dao.RoleDao;
import com.ezhixuan.xuan_framework.domain.entity.Role;
import com.ezhixuan.xuan_framework.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author Ezhixuan
 * @since 2023-10-01 00:21:14
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

}

