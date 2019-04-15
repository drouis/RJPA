package com.rjpa.service;

import com.rjpa.vo.AdminUserV;
import model.Result;

import java.util.List;

/**
 * @ClassName: com.rjpa.service.ISystemUserService
 * @Description: 系统用户服务类
 * @parm
 * @return
 * @author: drouis
 * @date: 2019/4/13 00:57
 */
public interface ISystemUserService {

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description: 获取平台全部用户，admin
     * @parm
     * @author: drouis
     * @date: 2019/4/13 00:57
     */
    public Result getAdmins();

    /**
     * @return com.rjpa.vo.AdminUserV
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description: 通过数据主键获取系统用户数据
     * @parm uid 系统用户数据主键ID
     * @author: drouis
     * @date: 2019/4/13 00:58
     */
    public AdminUserV getUserByUId(int uid);

    /**
     * @return
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description: 验证系统用户数据是否存在
     * @parm userName 登录名，phoneNumber 用户手机号
     * @author: drouis
     * @date: 2019/4/13 01:00
     */
    public Result checkUserParamExist(String userName, String phoneNumber);
    public boolean checkUserExist(String userName, String phoneNumber);

    /**
     * @return AdminUserV 系统用户信息
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description: 通过手机号或者用户名取得用户数据信息
     * @parm userName 用户登陆名 phoneNumber 用户手机号
     * @author: drouis
     * @date: 2019/4/14 22:55
     */
    public AdminUserV getSysUserInfoByUserNameOrPhoneNumber(String userName, String phoneNumber);

    /**
     * @return com.rjpa.vo.AdminUserV
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description:
     * @parm uid 系统用户数据主键ID
     * @author: drouis
     * @date: 2019/4/13 00:59
     */
    public AdminUserV checkUserExistByUId(int uid);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description: 添加平台用户
     * @parm com.rjpa.vo.adminUserV
     * @author: drouis
     * @date: 2019/4/13 00:56
     */
    public Result addAdmin(AdminUserV adminUserV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description: 修改平台用户
     * @parm com.rjpa.vo.adminUserV
     * @author: drouis
     * @date: 2019/4/13 00:56
     */
    public Result editAdmin(AdminUserV adminUserV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description: 停用平台用户
     * @parm com.rjpa.vo.adminUserV
     * @author: drouis
     * @date: 2019/4/13 00:55
     */
    public Result forbAdmin(AdminUserV adminUserV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description: 启动平台用户
     * @parm com.rjpa.vo.adminUserV
     * @author: drouis
     * @date: 2019/4/13 00:50
     */
    public Result actiAdmin(AdminUserV adminUserV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description: 删除平台用户
     * @parm adminId 系统用户数据主键ID
     * @author: drouis
     * @date: 2019/4/13 00:51
     */
    public Result delAdmin(int adminId);

    /**
     * @return List 绑定角色的系统用户列表
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description: 获取角色下所有绑定的系统用户
     * @parm rid 系统角色主键ID
     * @author: drouis
     * @date: 2019/4/13 00:51
     */
    public List<AdminUserV> getBundRoleUsers(int rid);

    /**
     * @return
     * @ClassName: com.rjpa.service.ISystemUserService
     * @Description: 绑定页面选定角色的系统用户
     * @parm bundUserIds 页面选定的用户数组，rid 系统角色主键ID
     * @author: drouis
     * @date: 2019/4/13 00:51
     */
    public void bundRoleUsers(String[] bundUserIds, int rid);
}
