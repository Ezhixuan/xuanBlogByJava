package com.ezhixuan.xuan_framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezhixuan.xuan_framework.domain.dto.tag.TagDTO;
import com.ezhixuan.xuan_framework.domain.dto.tag.TagPageDTO;
import com.ezhixuan.xuan_framework.domain.entity.Tag;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.tag.TagVo;

import java.util.List;

/**
 * 标签(Tag)表服务接口
 *
 * @author Ezhixuan
 * @since 2023-09-30 17:21:43
 */
public interface TagService extends IService<Tag> {

    /**
     * 标签列表
     * @return
     */
    ResponseResult<PageVo> selectTagPageSys(TagPageDTO tagPageDTO);

    /**
     * 保存标签
     * @param tagDTO
     * @return
     */
    ResponseResult<String> insertTagSys(TagDTO tagDTO);

    /**
     * 删除标签
     * @param ids
     * @return
     */
    ResponseResult<String> deleteTagByIdSys(List<Long> ids);

    /**
     * 获取标签
     * @return
     */
    ResponseResult<TagVo> selectTagByIdSys(Long id);

    /**
     * 更新标签
     * @param tagDTO
     * @return
     */
    ResponseResult<String> updateTagSys(TagDTO tagDTO);

    /**
     * 获取所有标签
     * @return
     */
    ResponseResult<List<TagVo>> selectTagListSys();

}

