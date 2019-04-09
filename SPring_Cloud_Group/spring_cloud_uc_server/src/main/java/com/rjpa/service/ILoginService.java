package com.rjpa.service;

import model.Result;

public interface ILoginService {

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    Result getUserByUserName(String userName);

    /**
     * 根据电话号码查询用户
     *
     * @param phone
     * @return
     */
    Result getUserByPhone(String phone);

    /**
     * 根据用户ID查询操作权限及菜单
     *
     * @param id
     * @return
     */
    Result getUserMenuRightsByUserId(Integer id);

    /**
     * 根据用户ID查询角色
     *
     * @param id
     * @return
     */
    Result getRoleByUserId(Integer id);

    /**
     * 查询全部操作权限URL
     *
     * @return
     */
    Result getAdminPermissionUrls();
}
