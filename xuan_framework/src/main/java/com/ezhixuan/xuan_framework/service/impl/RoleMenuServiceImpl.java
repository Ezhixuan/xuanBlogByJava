package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.dao.RoleMenuDao;
import com.ezhixuan.xuan_framework.domain.entity.RoleMenu;
import com.ezhixuan.xuan_framework.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author Ezhixuan
 * @since 2023-10-03 14:11:37
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenu> implements RoleMenuService {

}

