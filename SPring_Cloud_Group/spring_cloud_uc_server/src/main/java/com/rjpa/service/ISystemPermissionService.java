package com.rjpa.service;

import com.rjpa.vo.AdminPermissionV;
import model.Result;

import java.util.List;

/**
 * @ClassName: ISystemPermissionService
 * @Description: 平台权限服务类
 * @parm
 * @return
 * @author: drouis
 * @date: 2019/4/13 00:18
 */
public interface ISystemPermissionService {

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemPermissionService
     * @Description: 获取平台全部权限，permission
     * @parm
     * @author: drouis
     * @date: 2019/4/13 00:18
     */
    public Result getPermissions();

    /**
     * @return com.rjpa.vo.AdminPermissionV
     * @ClassName: com.rjpa.service.ISystemPermissionService
     * @Description: 通过主键ID获取单一权限信息
     * @parm id 主键ID
     * @author: drouis
     * @date: 2019/4/13 00:19
     */
    public AdminPermissionV getPermissionByPId(int pid);

    /**
     * @return com.rjpa.vo.AdminPermissionV
     * @ClassName: com.rjpa.service.ISystemPermissionService
     * @Description: 通过权限名称获取单一权限信息
     * @parm id 主键ID
     * @author: drouis
     * @date: 2019/4/13 00:19
     */
    public AdminPermissionV getPermissionByPermission(String permission);

    /**
     * @return boolean
     * @ClassName: com.rjpa.service.ISystemPermissionService
     * @Description: 验证权限是否在DB中存在
     * @parm name 权限名称，permission 权限标记
     * @author: drouis
     * @date: 2019/4/13 00:20
     */
    public boolean checkPermissionExist(String name, String permission);

    /**
     * @return com.rjpa.vo.AdminPermissionV
     * @ClassName: com.rjpa.service.ISystemPermissionService
     * @Description: 使用主键验证数据是否存在，并返回数据
     * @parm id 权限主键ID
     * @author: drouis
     * @date: 2019/4/13 00:21
     */
    public AdminPermissionV checkPermissionExistByPId(int pid);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemPermissionService
     * @Description: 添加平台权限
     * @parm adminPermissionV
     * @author: drouis
     * @date: 2019/4/13 00:25
     */
    public Result addPermission(AdminPermissionV adminPermissionV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemPermissionService
     * @Description: 修改平台权限
     * @parm adminPermissionV
     * @author: drouis
     * @date: 2019/4/13 00:26
     */
    public Result editPermission(AdminPermissionV adminPermissionV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemPermissionService
     * @Description: 删除平台权限
     * @parm PermissionId
     * @author: drouis
     * @date: 2019/4/13 00:26
     */
    public Result delPermission(int PermissionId);

    /**
     * @return List 权限列表
     * @ClassName: com.rjpa.service.ISystemPermissionService
     * @Description: 取得角色绑定的所有权限
     * @parm rid 角色表主键ID
     * @author: drouis
     * @date: 2019/4/13 00:27
     */
    public List<AdminPermissionV> getBundRolePermissions(int rid);

    /**
     * @return
     * @ClassName: com.rjpa.service.ISystemPermissionService
     * @Description: 绑定角色权限
     * @parm bundPermissionIds 页面分配的权限Id数组，rid 角色表主键ID
     * @author: drouis
     * @date: 2019/4/13 00:27
     */
    public void bundRolePermissions(String[] bundPermissionIds, int rid);
}
