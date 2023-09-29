package com.ezhixuan.xuan_framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezhixuan.xuan_framework.domain.entity.Link;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.link.LinkListVo;

import java.util.List;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-09-25 16:48:42
 */
public interface LinkService extends IService<Link> {

    /**
     * 查询所有友联信息
     * @return
     */
    ResponseResult<List<LinkListVo>> queryAllLink();
}
