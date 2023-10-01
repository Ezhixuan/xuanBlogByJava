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
import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.tag.TagVo;
import com.ezhixuan.xuan_framework.exception.BaseException;
import com.ezhixuan.xuan_framework.service.ArticleTagService;
import com.ezhixuan.xuan_framework.service.TagService;
import com.ezhixuan.xuan_framework.utils.BeanUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
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
  public ResponseResult<PageVo> tagList(TagPageDTO tagPageDTO) {
    // 1. 校验参数
    tagPageDTO.check();
    // 2. 构建查询条件
    LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
    // 2.1 当标签名存在时查询标签名
    tagLambdaQueryWrapper.like(
        StringUtils.hasText(tagPageDTO.getName()), Tag::getName, tagPageDTO.getName());
    // 2.2 当备注存在时查询备注
    tagLambdaQueryWrapper.like(
        StringUtils.hasText(tagPageDTO.getRemark()), Tag::getRemark, tagPageDTO.getRemark());
    // 2.3 根据更新时间排序
    tagLambdaQueryWrapper.orderByDesc(Tag::getUpdateTime);
    // 3. 分页查询
    Page<Tag> tagPage = new Page<Tag>(tagPageDTO.getPageNum(), tagPageDTO.getPageSize());
    page(tagPage, tagLambdaQueryWrapper);
    // 4. 封装返回
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
  public ResponseResult<String> save(TagDTO tagDTO) {
    // 1. 校验参数
    if (tagDTO == null) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    } else if (!StringUtils.hasText(tagDTO.getName())) {
      throw new BaseException("标签名不能为空");
    }
    if (!StringUtils.hasText(tagDTO.getRemark())) {
      tagDTO.setRemark("无");
    }
    // 2. 封装保存
    Tag tag = BeanUtil.copyBean(tagDTO, Tag.class);
    save(tag);
    // 3. 返回
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
  public ResponseResult<String> deleteTag(List<Long> ids) {
    // 1. 校验参数
    if (ObjectUtils.isEmpty(ids)) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 删除文章标签关联
    ids.forEach(
        id -> {
          articleTagService.remove(Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getTagId, id));
        });
    // 3. 删除标签
    removeByIds(ids);
    // 4. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 获取标签
   *
   * @return
   */
  @Override
  public ResponseResult<TagVo> getTag(Long id) {
    // 1. 校验参数
    if (ObjectUtils.isEmpty(id)) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
    // 2. 查询
    Tag tag = getById(id);
    // 3. 封装返回
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
  public ResponseResult<String> updateTag(TagDTO tagDTO) {
    // 1. 校验参数
    if (tagDTO == null) {
      throw new BaseException(AppHttpCodeEnum.DATA_NOT_EXIST);
    } else if(ObjectUtils.isEmpty(tagDTO.getId())){
      throw new BaseException("标签id不能为空");
    }else if (!StringUtils.hasText(tagDTO.getName())) {
      throw new BaseException("标签名不能为空");
    }
    if (!StringUtils.hasText(tagDTO.getRemark())) {
      tagDTO.setRemark("无");
    }
    // 2. 封装更新
    Tag tag = BeanUtil.copyBean(tagDTO, Tag.class);
    updateById(tag);
    // 3. 返回
    return ResponseResult.SUCCESS;
  }

  /**
   * 获取所有标签
   *
   * @return
   */
  @Override
  public ResponseResult<List<TagVo>> listAllTag() {
    // 1. 查询所有标签
    List<Tag> list = list();
    // 2. 封装返回
    List<TagVo> tagVos = BeanUtil.copyBeanList(list, TagVo.class);
    return ResponseResult.okResult(tagVos);
  }


}
