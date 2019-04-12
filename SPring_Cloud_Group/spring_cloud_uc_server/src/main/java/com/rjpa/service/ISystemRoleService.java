package com.rjpa.service;

import com.rjpa.vo.AdminRoleV;
import model.Result;

/**
 * @ClassName: ISystemRoleService
 * @Description: 平台角色服务类
 * @parm
 * @return
 * @author: drouis
 * @date: 2019/4/13 00:58
 */
public interface ISystemRoleService {
    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemRoleService
     * @Description: 获取平台全部角色，AdminRoleV
     * @parm
     * @author: drouis
     * @date: 2019/4/13 00:49
     */
    public Result getRoles();

    /**
     * @return com.rjpa.vo.AdminRoleV
     * @ClassName: com.rjpa.service.ISystemRoleService
     * @Description: 通过角色数据主键ID获取角色数据
     * @parm rid 角色数据主键ID
     * @author: drouis
     * @date: 2019/4/13 00:48
     */
    public AdminRoleV getRoleByRId(int rid);

    /**
     * @return
     * @ClassName: com.rjpa.service.ISystemRoleService
     * @Description: 验证角色数据是否存在
     * @parm name 角色名称，role 角色数据标示
     * @author: drouis
     * @date: 2019/4/13 01:01
     */
    public boolean checkRoleExist(String name, String role);

    /**
     * @return com.rjpa.vo.AdminRoleV
     * @ClassName: com.rjpa.service.ISystemRoleService
     * @Description: 通过数据主键ID验证角色是否存在
     * @parm rid 角色数据主键ID
     * @author: drouis
     * @date: 2019/4/13 00:49
     */
    public AdminRoleV checkRoleExistByRId(int rid);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemRoleService
     * @Description: 添加平台角色
     * @parm com.rjpa.vo.adminRoleV
     * @author: drouis
     * @date: 2019/4/13 00:47
     */
    public Result addRole(AdminRoleV adminRoleV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemRoleService
     * @Description: 修改平台角色
     * @parm com.rjpa.vo.adminRoleV
     * @author: drouis
     * @date: 2019/4/13 00:47
     */
    public Result editRole(AdminRoleV adminRoleV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemRoleService
     * @Description: 停用平台角色
     * @parm com.rjpa.vo.adminRoleV
     * @author: drouis
     * @date: 2019/4/13 00:48
     */
    public Result forbRole(AdminRoleV adminRoleV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemRoleService
     * @Description: 启动平台角色
     * @parm com.rjpa.vo.adminRoleV
     * @author: drouis
     * @date: 2019/4/13 00:48
     */
    public Result actiRole(AdminRoleV adminRoleV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemRoleService
     * @Description: 删除平台角色
     * @parm rid 角色数据主键ID
     * @author: drouis
     * @date: 2019/4/13 00:48
     */
    public Result delRole(int rId);

}
