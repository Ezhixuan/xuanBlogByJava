package com.ezhixuan.xuan_framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezhixuan.xuan_framework.domain.dto.role.RolePageDTO;
import com.ezhixuan.xuan_framework.domain.dto.role.RoleSaveDTO;
import com.ezhixuan.xuan_framework.domain.entity.Role;
import com.ezhixuan.xuan_framework.domain.vo.PageVo;
import com.ezhixuan.xuan_framework.domain.vo.ResponseResult;
import com.ezhixuan.xuan_framework.domain.vo.role.RoleVo;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author Ezhixuan
 * @since 2023-10-01 00:21:14
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询角色列表
     *
     * @param rolePageDTO
     * @return
     */
    ResponseResult<PageVo> queryList(RolePageDTO rolePageDTO);

    /**
     * 修改角色状态
     * @param role
     * @return
     */
    ResponseResult<String> updateStatus(Role role);

    /**
     * 新增角色
     * @param roleDTO
     * @return
     */
    ResponseResult<String> saveByDto(RoleSaveDTO roleDTO);

    /**
     * 获取角色信息
     * @param id
     * @return
     */
    ResponseResult<RoleVo> get(Long id);

    /**
     * 修改角色信息
     * @param roleDTO
     * @return
     */
    ResponseResult<String> updateByDto(RoleSaveDTO roleDTO);

    /**
     * 删除角色
     * @param ids
     * @return
     */
    ResponseResult<String> delete(List<Long> ids);

    /**
     * 查询所有角色
     * @return
     */
    ResponseResult<List<Role>> queryAll();

}

