package com.hotel.system.service;

import com.hotel.system.common.base.BaseService;
import com.hotel.system.entity.Role;

import java.util.List;

/**
 * 角色逻辑接口
 * @author liuyanzhao
 * @date 2022/04/26
 */
public interface RoleService extends BaseService<Role, Long> {

    /**
     * 删除某个用户的所有关联
     *
     * @param userId 用户Id
     */
    void deleteByUserId(Long userId);

    /**
     * 根据编号查询单个权限
     *
     * @param roleId roleId
     * @return Role
     */
    Role findByRoleId(Long roleId);

    /**
     * 根据编号查询单个权限
     *
     * @param roleName roleName
     * @return Role
     */
    Role findByRoleName(String roleName);

    /**
     * 根据用户Id获得角色
     *
     * @param userId 用户Id
     * @return 角色列表
     */
    Role findByUserId(Long userId);

    /**
     * 统计这个角色的用户数
     *
     * @param roleId 角色Id
     */
    Integer countUserByRoleId(Long roleId);

    /**
     * 查询某个用户最大的角色等级
     * @param userId
     * @return
     */
    Integer findMaxLevelByUserId(Long userId);

    /**
     * 查询小于等于该等级的角色
     * @param level
     * @return
     */
    List<Role> findByLessThanLevel(Integer level);

    /**
     * 获得用户注册默认角色
     * @return
     */
    Role findDefaultRole();

    /**
     * 获得用户注册默认角色
     * @return
     */
    Role getMaxRoleByUserId(Long userId);

}
