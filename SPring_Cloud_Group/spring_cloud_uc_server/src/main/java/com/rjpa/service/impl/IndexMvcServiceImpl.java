package com.rjpa.service.impl;

import com.rjpa.repository.*;
import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;
import com.rjpa.repository.Entity.LzhAdminPermissionEntity;
import com.rjpa.repository.Entity.LzhAdminRolePermissionEntity;
import com.rjpa.repository.Entity.LzhAdminUserRoleEntity;
import com.rjpa.service.IndexMvcService;
import model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Result getUserMenusRights(int parentid, int uid) {
        List menus = new ArrayList();
        // TODO 根据用户id关联查询用户绑定的所有角色
        List<LzhAdminUserRoleEntity> userRoles = adminUserRoleRepository.findByUserId(uid);
        for(LzhAdminUserRoleEntity role:userRoles){
            List<LzhAdminRolePermissionEntity> rolePermissionLinks = adminRolePermissionRepository.findByRoleId(role.getRoleId());
            for(LzhAdminRolePermissionEntity roleLink:rolePermissionLinks){
                LzhAdminPermissionEntity permissionEntity = adminPermissionRepository.findByIdAndAndDescriptionNotNull(new Long(roleLink.getPermissionId()).intValue());
                if(null != permissionEntity){
                    LzhAdminMenusRightsEntity menu = (LzhAdminMenusRightsEntity)adminMenusRightsRepository.findByPermission(permissionEntity.getPermission()).get(0);
                    menus.add(menu);
                }
            }
        }
        return Result.ok(menus);
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
