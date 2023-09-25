package com.ezhixuan.xuan_framework.service.impl;

import static com.ezhixuan.xuan_framework.constant.CommonConstant.LINK_STATUS_PASSED;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.dao.LinkDao;
import com.ezhixuan.xuan_framework.domain.entity.Link;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.link.LinkListVo;
import com.ezhixuan.xuan_framework.service.LinkService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-09-25 16:48:42
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkDao, Link> implements LinkService {

  /**
   * 查询所有友联信息
   *
   * @return
   */
  @Override
  public ResponseResult queryAllLink() {
    // 在友链页面显示所有审核通过的友链信息
    // 1. 构建查询
    List<Link> list = list(Wrappers.<Link>lambdaQuery().eq(Link::getStatus, LINK_STATUS_PASSED));
    // 2. 封装
    List<LinkListVo> linkListVos = BeanUtil.copyBeanList(list, LinkListVo.class);
    // 3. 返回
    return ResponseResult.okResult(linkListVos);
  }
}
