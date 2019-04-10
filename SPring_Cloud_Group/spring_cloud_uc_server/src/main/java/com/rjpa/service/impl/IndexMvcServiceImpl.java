package com.rjpa.service.impl;

import com.rjpa.repository.*;
import com.rjpa.service.IndexMvcService;
import model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户中心用户信息维护界面业务service
 */
@Service
public class IndexMvcServiceImpl implements IndexMvcService {
    @Override
    public Result getAllAdminMenusRights() {
        return Result.ok(adminMenusRightsRepository.getLzhAdminMenusRightsEntitiesByParentidOrderByClassesAsc(-1));
    }

    /**
     * 获取全部主菜单，二级菜单，adminMenusRights
     *
     * @return
     */
    @Override
    public Result getAdminMenusRights(int parentid) {
        return Result.ok(adminMenusRightsRepository.getLzhAdminMenusRightsEntitiesByParentidOrderByClassesAsc(parentid));
    }

    /**
     * 获取全部系统操作权限名称链接，adminPermission
     *
     * @return
     */
    @Override
    public Result getAdminPermission() {
        return Result.ok(adminPermissionRepository.findAll());
    }

    /**x
     * 获取全部权限，adminRole
     *
     * @return
     */
    @Override
    public Result getAdminRole() {
        return Result.ok(adminRoleRepository.findAll());
    }

    /**
     * 获取全部用户角色绑定的权限，adminRolePermission
     *
     * @return
     */
    @Override
    public Result getAdminRolePermission() {
        return Result.ok(adminRolePermissionRepository.findAll());
    }

    /**
     * 获取全部绑定权限的用户，adminUserRole
     *
     * @return
     */
    @Override
    public Result getAdminUserRole() {
        return Result.ok(adminUserRoleRepository.findAll());
    }

    /**
     * 获取平台全部用户，admin
     *
     * @return
     */
    @Override
    public Result getAdmin() {
        return Result.ok(adminRepository.findAll());
    }

    @Autowired
    LzhAdminMenusRightsRepository adminMenusRightsRepository;
    @Autowired
    LzhAdminPermissionRepository adminPermissionRepository;
    @Autowired
    LzhAdminRepository adminRepository;
    @Autowired
    LzhAdminRolePermissionRepository adminRolePermissionRepository;
    @Autowired
    LzhAdminRoleRepository adminRoleRepository;
    @Autowired
    LzhAdminUserRoleRepository adminUserRoleRepository;
}
