package com.rjpa.service;

import model.Result;

public interface IndexMvcService {

    // TODO 权限管理 MenusPermissionMvcController

    /**
     * 获取全部主菜单，二级菜单，adminMenusRights
     *
     * @return
     */
    public Result getAdminMenusRights(int parentid);
    public Result getUserMenusRights(int parentid,int uid);
    public Result getAllAdminMenusRights();
    /**
     * 获取全部系统操作权限名称链接，adminPermission
     *
     * @return
     */
    public Result getAdminPermission();

    // TODO 角色管理 RolePermissionMvcController

    /**
     * 获取全部权限，adminRole
     *
     * @return
     */
    public Result getAdminRole();

    /**
     * 获取全部用户角色绑定的权限，adminRolePermission
     *
     * @return
     */
    public Result getAdminRolePermission();

    // TODO 用户管理 UserRoleMvcController

    /**
     * 获取全部绑定权限的用户，adminUserRole
     *
     * @return
     */
    public Result getAdminUserRole();

    /**
     * 获取平台全部用户，admin
     *
     * @return
     */
    public Result getAdmin();
}
