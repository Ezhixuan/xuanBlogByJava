package com.ezhixuan.xuan_framework.service.impl;

import static com.ezhixuan.xuan_framework.constant.CommonConstant.LINK_STATUS_PASSED;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.dao.LinkDao;
import com.ezhixuan.xuan_framework.domain.dto.link.LinkDTO;
import com.ezhixuan.xuan_framework.domain.dto.link.LinkPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Link;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.link.LinkListVo;
import com.ezhixuan.xuan_framework.service.LinkService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
   * @return 友联信息
   */
  @Override
  public ResponseResult<List<LinkListVo>> selectLinkList() {
    // 构建查询
    List<Link> list = list(Wrappers.<Link>lambdaQuery().eq(Link::getStatus, LINK_STATUS_PASSED));
    List<LinkListVo> linkListVos = BeanUtil.copyBeanList(list, LinkListVo.class);
    return ResponseResult.okResult(linkListVos);
  }

  /**
   * 友链列表
   *
   * @param linkDTO 分页参数
   * @return 友链列表
   */
  @Override
  public ResponseResult<PageVo> selectLinkPageSys(LinkPageDTO linkDTO) {
    linkDTO.check();
    // 构建查询
    LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
    linkLambdaQueryWrapper.like(
        StringUtils.hasText(linkDTO.getName()), Link::getName, linkDTO.getName());
    if (StringUtils.hasText(linkDTO.getStatus())) {
      linkLambdaQueryWrapper.eq(Link::getStatus, linkDTO.getStatus());
    } else {
      linkLambdaQueryWrapper.eq(Link::getStatus, LINK_STATUS_PASSED);
    }
    // 分页查询
    Page<Link> linkPage = new Page<>(linkDTO.getPageNum(), linkDTO.getPageSize());
    page(linkPage, linkLambdaQueryWrapper);
    // 封装返回
    List<LinkListVo> linkListVos = BeanUtil.copyBeanList(linkPage.getRecords(), LinkListVo.class);
    PageVo pageVo = new PageVo(linkListVos, linkPage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 修改友链
   *
   * @param linkDto 友链信息
   * @return 修改结果
   */
  @Override
  public ResponseResult<String> updateLinkSys(LinkDTO linkDto) {
    Link link = BeanUtil.copyBean(linkDto, Link.class);
    updateById(link);
    return ResponseResult.SUCCESS;
  }

  /**
   * 添加友链
   *
   * @param linkDto 友链信息
   * @return 添加结果
   */
  @Override
  public ResponseResult<String> insertLinkSys(LinkDTO linkDto) {
    Link link = BeanUtil.copyBean(linkDto, Link.class);
    save(link);
    return ResponseResult.SUCCESS;
  }
}
