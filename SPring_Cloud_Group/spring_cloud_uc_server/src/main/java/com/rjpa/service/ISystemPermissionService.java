package com.rjpa.service;

import com.rjpa.vo.AdminPermissionV;
import model.Result;

import java.util.List;

public interface ISystemPermissionService {

    /**
     * 获取平台全部用户，admin
     *
     * @return
     */
    public Result getPermissions();

    public AdminPermissionV getPermissionByRId(int id);

    public boolean checkPermissionExist(String name,String permission);

    public AdminPermissionV checkPermissionExistByUId(int id);

    /**
     * 添加平台权限
     *
     * @param adminPermissionV
     * @return
     */
    public Result addPermission(AdminPermissionV adminPermissionV);

    /**
     * 修改平台权限
     *
     * @param adminPermissionV
     * @return
     */
    public Result editPermission(AdminPermissionV adminPermissionV);

    /**
     * 删除平台权限
     *
     * @param PermissionId
     * @return
     */
    public Result delPermission(int PermissionId);

    public List<AdminPermissionV> getBundRolePermissions(int rid);

    public void bundRolePermissions(String[] bundPermissionIds, int rid);
}
