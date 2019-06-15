package com.rjpa.service.impl;

import com.rjpa.repository.Entity.*;
import com.rjpa.repository.*;
import com.rjpa.service.ILoginService;
import com.rjpa.vo.AdminPermissionV;
import com.rjpa.vo.AdminRoleV;
import model.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 登陆界面业务service
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Override
    public Result getUserByUserName(String userName) {
        Result r = new Result();
        List<LzhAdminEntity> ls = adminRepository.findByUserName(userName);
        if (null != ls && 0 < ls.size()) {
            return new Result().ok(ls.get(0));
        }
        return r;
    }

    @Override
    public Result getUserByPhone(String phone) {
        Result r = new Result();
        List<LzhAdminEntity> ls = adminRepository.findByPhoneNumber(phone);
        if (null != ls && 0 < ls.size()) {
            return new Result().ok(ls.get(0));
        }
        return r;
    }

    /**
     * @return
     * @ClassName: com.rjpa.service.impl.LoginServiceImpl
     * @Description: 用户ID数据主键查询绑定角色的全部权限信息
     * @parm
     * @author: drouis
     * @date: 2019/4/14 17:14
     */
    @Override
    public Result getUserMenuRightsByUserId(Integer id) {
        List<AdminPermissionV> vList = new ArrayList<AdminPermissionV>();
        // TODO 1.1 取得全部角色信息
        List<LzhAdminUserRoleEntity> roleLinks = adminUserRoleRepository.findByUserId(new Long(id));
        List<AdminRoleV> userRoles = new ArrayList<AdminRoleV>();
        // TODO 1.2 循环每一个角色信息
        for (LzhAdminUserRoleEntity roleLink : roleLinks) {
            // TODO 1.3 查询角色实体数据
            LzhAdminRoleEntity roleDB = (LzhAdminRoleEntity) adminRoleRepository.findById(new Long(roleLink.getRoleId()).intValue());
            // TODO 1.4 查询角色关联权限数据
            List<LzhAdminRolePermissionEntity> permissionLinks = adminRolePermissionRepository.findByRoleId(roleDB.getId());
            for (LzhAdminRolePermissionEntity permissionLink : permissionLinks) {
                // TODO 1.5 查询权限实体数据
                LzhAdminPermissionEntity permissionDB = adminPermissionRepository.findById(new Long(permissionLink.getId()).intValue());
                AdminPermissionV v = new AdminPermissionV();
                BeanUtils.copyProperties(permissionDB, v);
                vList.add(v);
            }
        }
        return Result.ok(vList);
    }

    /**
     * @return
     * @ClassName: com.rjpa.service.impl.LoginServiceImpl
     * @Description: 用户ID数据主键查询绑定的全部角色信息
     * @parm
     * @author: drouis
     * @date: 2019/4/14 17:13
     */
    @Override
    public Result getRoleByUserId(Integer id) {
        List<AdminRoleV> vList = new ArrayList<AdminRoleV>();
        // TODO 1.1 根据用户ID取得绑定的所有角色
        List<LzhAdminUserRoleEntity> roles = adminUserRoleRepository.findByUserId(new Long(id));
        // TODO 1.2 循环关联数据表，并查询实体数据
        for (LzhAdminUserRoleEntity roleLinks : roles) {
            LzhAdminRoleEntity roleDBObj = (LzhAdminRoleEntity) adminRoleRepository.findById(new Long(roleLinks.getRoleId()).intValue());
            AdminRoleV v = new AdminRoleV();
            BeanUtils.copyProperties(roleDBObj, v);
            vList.add(v);
        }
        // TODO 1.3 返回查询结果
        return Result.ok(vList);
    }

    /**
     * @return
     * @ClassName: com.rjpa.service.impl.LoginServiceImpl
     * @Description: 查询系统中的全部权限信息
     * @parm
     * @author: drouis
     * @date: 2019/4/14 17:15
     */
    @Override
    public Result getAdminPermissionUrls() {
        List<AdminPermissionV> vList = new ArrayList<AdminPermissionV>();
        List<LzhAdminPermissionEntity> permissionDBList = adminPermissionRepository.findAll();
        for (LzhAdminPermissionEntity permissionDB : permissionDBList) {
            AdminPermissionV v = new AdminPermissionV();
            BeanUtils.copyProperties(permissionDB, v);
            vList.add(v);
        }
        return Result.ok(vList);
    }

    @Override
    public Result getAdminPermissionByUrl(String url) {
        List<AdminPermissionV> vList = new ArrayList<AdminPermissionV>();
        List<LzhAdminPermissionEntity> permissionDBList = adminPermissionRepository.findByPermissionUrlLike("%"+url+"%");
        for (LzhAdminPermissionEntity permissionDB : permissionDBList) {
            AdminPermissionV v = new AdminPermissionV();
            BeanUtils.copyProperties(permissionDB, v);
            vList.add(v);
        }
        return Result.ok(vList);
    }

    @Autowired
    LzhAdminRepository adminRepository;
    @Autowired
    LzhAdminPermissionRepository adminPermissionRepository;
    @Autowired
    LzhAdminRolePermissionRepository adminRolePermissionRepository;

    @Autowired
    LzhAdminUserRoleRepository adminUserRoleRepository;
    @Autowired
    LzhAdminRoleRepository adminRoleRepository;
}
