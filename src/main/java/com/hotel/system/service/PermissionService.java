package com.hotel.system.service;

import com.hotel.system.common.base.BaseService;
import com.hotel.system.entity.Permission;

import java.util.List;
import java.util.Set;

/**
 * 权限逻辑接口
 * @author liuyanzhao
 * @date 2022/04/26
 */
public interface PermissionService extends BaseService<Permission, Long> {

    /**
     * 根据角色Id获得权限列表
     *
     * @param roleId 角色Id
     * @return 权限列表
     */
    List<Permission> listPermissionsByRoleId(Long roleId);

    /**
     * 获得某个用户的权限URL列表
     *
     * @param userId
     * @return
     */
    Set<String> findPermissionUrlsByUserId(Long userId);

    /**
     * 获得某个用户的用户ID和资源类型
     *
     * @param userId
     * @param resourceType
     * @return
     */
    List<Permission> findPermissionTreeByUserIdAndResourceType(Long userId, String resourceType);

    /**
     * 根据角色ID获得权限列表
     * @param roleId
     * @return
     */
    List<Permission> findPermissionByRoleId(Long roleId);

    /**
     * 获得所有权限，带有等级
     * @return
     */
    List<Permission> findPermissionListWithLevel();

    /**
     * 统计子节点数量
     * @param id
     * @return
     */
    Integer countChildPermission(Long id);

    /**
     * 根据URL获得权限
     * @param url
     * @return
     */
    Permission findByUrl(String url);
}
