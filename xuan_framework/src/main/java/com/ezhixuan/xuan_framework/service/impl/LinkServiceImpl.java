package com.ezhixuan.xuan_framework.service.impl;

import static com.ezhixuan.xuan_framework.constant.CommonConstant.LINK_STATUS_PASSED;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.dao.LinkDao;
import com.ezhixuan.xuan_framework.domain.dto.link.LinkPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Link;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.link.LinkListVo;
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.service.LinkService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
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
   * @return
   */
  @Override
  public ResponseResult<List<LinkListVo>> queryAllLink() {
    // 在友链页面显示所有审核通过的友链信息
    // 1. 构建查询
    List<Link> list = list(Wrappers.<Link>lambdaQuery().eq(Link::getStatus, LINK_STATUS_PASSED));
    // 2. 封装
    List<LinkListVo> linkListVos = BeanUtil.copyBeanList(list, LinkListVo.class);
    // 3. 返回
    return ResponseResult.okResult(linkListVos);
  }

  /**
   * 友链列表
   *
   * @param linkDTO
   * @return
   */
  @Override
  public ResponseResult<PageVo> queryList(LinkPageDTO linkDTO) {
    // 1. 校验参数
    linkDTO.check();
    // 2. 构建查询
    LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
    // 2.1 如果友链名存在，按照友链名模糊查询
    linkLambdaQueryWrapper.like(StringUtils.hasText(linkDTO.getName()), Link::getName, linkDTO.getName());
    // 2.2 如果状态存在，按照状态查询，否则查询正常状态的
    if (StringUtils.hasText(linkDTO.getStatus())) {
      linkLambdaQueryWrapper.eq(Link::getStatus, linkDTO.getStatus());
    } else {
      linkLambdaQueryWrapper.eq(Link::getStatus, LINK_STATUS_PASSED);
    }
    // 3. 分页查询
    Page<Link> linkPage = new Page<>(linkDTO.getPageNum(), linkDTO.getPageSize());
    page(linkPage, linkLambdaQueryWrapper);
    // 4. 封装返回
    List<LinkListVo> linkListVos = BeanUtil.copyBeanList(linkPage.getRecords(), LinkListVo.class);
    PageVo pageVo = new PageVo(linkListVos, linkPage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 删除友链
   *
   * @param ids
   * @return
   */
  @Override
  public ResponseResult<String> remove(List<Long> ids) {
    
    // 1. 校验参数
    if (ObjectUtils.isEmpty(ids)){
      throw new BaseException("请选择要删除的友链");
    }
    // 2. 删除
    remove(ids);
    // 3. 返回
    return ResponseResult.SUCCESS;
  }
}
