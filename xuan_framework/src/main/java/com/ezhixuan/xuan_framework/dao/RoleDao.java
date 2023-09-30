package com.ezhixuan.xuan_framework.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezhixuan.xuan_framework.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author Ezhixuan
 * @since 2023-10-01 00:21:14
 */
public interface RoleDao extends BaseMapper<Role> {

    List<String> queryUserRole(Long id);
}

