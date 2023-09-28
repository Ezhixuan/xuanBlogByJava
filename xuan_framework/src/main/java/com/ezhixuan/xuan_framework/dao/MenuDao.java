package com.ezhixuan.xuan_framework.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezhixuan.xuan_framework.domain.entity.Menu;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-27 18:09:44
 */
public interface MenuDao extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

}
