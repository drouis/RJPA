package com.rjpa.service;

import com.rjpa.vo.AdminUserV;
import model.Result;

public interface ISystemRoleUserService {
    /**
     * 获取平台全部用户，admin
     *
     * @return
     */
    public Result getAdmin();

    /**
     * 添加平台用户
     *
     * @param adminUserV
     * @return
     */
    public Result addAdmin(AdminUserV adminUserV);

    /**
     * 修改平台用户
     *
     * @param adminUserV
     * @return
     */
    public Result editAdmin(AdminUserV adminUserV);

    /**
     * 停用平台用户
     *
     * @param adminUserV
     * @return
     */
    public Result forbAdmin(AdminUserV adminUserV);

    /**
     * 启动平台用户
     *
     * @param adminUserV
     * @return
     */
    public Result actiAdmin(AdminUserV adminUserV);

    /**
     * 删除平台用户
     *
     * @param adminId
     * @return
     */
    public Result delAdmin(int adminId);
}
