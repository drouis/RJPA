package com.rjpa.service;

import com.rjpa.vo.AdminRoleV;
import model.Result;

public interface ISystemRoleService {
    /**
     * 获取平台全部角色，AdminRoleV
     *
     * @return
     */
    public Result getRoles();

    public AdminRoleV getRoleByRId(int id);

    public boolean checkRoleExist(String name, String role);

    public AdminRoleV checkRoleExistByUId(int id);

    /**
     * 添加平台角色
     *
     * @param adminRoleV
     * @return
     */
    public Result addRole(AdminRoleV adminRoleV);

    /**
     * 修改平台角色
     *
     * @param adminRoleV
     * @return
     */
    public Result editRole(AdminRoleV adminRoleV);

    /**
     * 停用平台角色
     *
     * @param adminRoleV
     * @return
     */
    public Result forbRole(AdminRoleV adminRoleV);

    /**
     * 启动平台角色
     *
     * @param adminRoleV
     * @return
     */
    public Result actiRole(AdminRoleV adminRoleV);

    /**
     * 删除平台角色
     *
     * @param rId
     * @return
     */
    public Result delRole(int rId);

}
