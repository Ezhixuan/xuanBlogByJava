package com.ezhixuan.xuan_framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezhixuan.xuan_framework.dao.TagDao;
import com.ezhixuan.xuan_framework.domain.dto.tag.TagDTO;
import com.ezhixuan.xuan_framework.domain.dto.tag.TagPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.ArticleTag;
import com.ezhixuan.xuan_framework.domain.entity.Tag;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.tag.TagVo;
import com.ezhixuan.xuan_framework.service.ArticleTagService;
import com.ezhixuan.xuan_framework.service.TagService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 标签(Tag)表服务实现类
 *
 * @author Ezhixuan
 * @since 2023-09-30 17:21:43
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {

  @Resource private ArticleTagService articleTagService;

  /**
   * 标签列表
   *
   * @return
   */
  @Override
  public ResponseResult<PageVo> selectTagPageSys(TagPageDTO tagPageDTO) {
    tagPageDTO.check();
    // 构建查询条件
    LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
    tagLambdaQueryWrapper.like(
        StringUtils.hasText(tagPageDTO.getName()), Tag::getName, tagPageDTO.getName());
    tagLambdaQueryWrapper.like(
        StringUtils.hasText(tagPageDTO.getRemark()), Tag::getRemark, tagPageDTO.getRemark());
    tagLambdaQueryWrapper.orderByDesc(Tag::getUpdateTime);
    // 分页查询
    Page<Tag> tagPage = new Page<Tag>(tagPageDTO.getPageNum(), tagPageDTO.getPageSize());
    page(tagPage, tagLambdaQueryWrapper);
    // 封装返回
    List<TagVo> tagVos = BeanUtil.copyBeanList(tagPage.getRecords(), TagVo.class);
    PageVo pageVo = new PageVo(tagVos, tagPage.getTotal());
    return ResponseResult.okResult(pageVo);
  }

  /**
   * 保存标签
   *
   * @param tagDTO
   * @return
   */
  @Override
  public ResponseResult<String> insertTagSys(TagDTO tagDTO) {
    if (!StringUtils.hasText(tagDTO.getRemark())) {
      tagDTO.setRemark("无");
    }
    // 封装保存
    Tag tag = BeanUtil.copyBean(tagDTO, Tag.class);
    save(tag);
    return ResponseResult.SUCCESS;
  }

  /**
   * 删除标签
   *
   * @param ids
   * @return
   */
  @Override
  @Transactional
  public ResponseResult<String> deleteTagByIdSys(List<Long> ids) {
    ids.forEach(
        id -> {
          articleTagService.remove(Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getTagId, id));
        });
    removeByIds(ids);
    return ResponseResult.SUCCESS;
  }

  /**
   * 获取标签
   *
   * @return
   */
  @Override
  public ResponseResult<TagVo> selectTagByIdSys(Long id) {
    Tag tag = getById(id);
    TagVo tagVo = BeanUtil.copyBean(tag, TagVo.class);
    return ResponseResult.okResult(tagVo);
  }

  /**
   * 更新标签
   *
   * @param tagDTO
   * @return
   */
  @Override
  public ResponseResult<String> updateTagSys(TagDTO tagDTO) {
    if (!StringUtils.hasText(tagDTO.getRemark())) {
      tagDTO.setRemark("无");
    }
    // 封装更新
    Tag tag = BeanUtil.copyBean(tagDTO, Tag.class);
    updateById(tag);
    return ResponseResult.SUCCESS;
  }

  /**
   * 获取所有标签
   *
   * @return
   */
  @Override
  public ResponseResult<List<TagVo>> selectTagListSys() {
    List<Tag> list = list();
    // 封装返回
    List<TagVo> tagVos = BeanUtil.copyBeanList(list, TagVo.class);
    return ResponseResult.okResult(tagVos);
  }
}
