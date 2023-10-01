package com.ezhixuan.xuan_framework.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezhixuan.xuan_framework.domain.entity.Tag;

/**
 * 标签(Tag)表数据库访问层
 *
 * @author Ezhixuan
 * @since 2023-09-30 17:21:43
 */
public interface TagDao extends BaseMapper<Tag> {

    /**
     * 删除标签与文章的关联关系
     * @param id
     */
    void deleteRelationship(Long id);
}

