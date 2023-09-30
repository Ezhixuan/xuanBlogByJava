package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.dao.TagDao;
import com.ezhixuan.xuan_framework.domain.entity.Tag;
import com.ezhixuan.xuan_framework.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author Ezhixuan
 * @since 2023-09-30 17:21:43
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {

}

