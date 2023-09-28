package com.ezhixuan.xuan_framework.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezhixuan.xuan_framework.domain.entity.User;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-27 18:04:00
 */
public interface UserDao extends BaseMapper<User> {

    String queryUserName(Long id);

}
