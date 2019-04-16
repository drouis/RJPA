package com.rjpa;

import com.rjpa.service.ISystemMenuService;
import com.rjpa.service.ISystemPermissionService;
import com.rjpa.service.impl.SystemMenuServiceImpl;
import com.rjpa.vo.AdminMenuV;
import com.rjpa.vo.AdminPermissionV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InitPermissionIntoDBStartup {
    public void initPermissionIntoDBStartup() {
        // TODO 根据权限列表更新系统权限数据库表
        List<AdminPermissionV> permissionVList = new ApplicationPermissionScanStartup().getPermissionsList();
        for (AdminPermissionV permissionV : permissionVList) {
            // TODO 验证权限列表中的数据是否在DB中存在，并添加不存在的权限。
            boolean check = iSystemPermissionService.checkPermissionExist(permissionV.getName(), permissionV.getPermission());
            if (!check) {
                iSystemPermissionService.addPermission(permissionV);
            } else {
                AdminPermissionV dbData = iSystemPermissionService.getPermissionByPermission(permissionV.getPermission());
                permissionV.setId(dbData.getId());
                iSystemPermissionService.editPermission(permissionV);
            }
        }
        List<AdminMenuV> menuVList = (List) iSystemMenuService.getMenus().getData();
        for (AdminMenuV menuV : menuVList) {
            // TODO 验证权限列表中的数据是否在DB中存在，并添加不存在的权限。
            boolean check = iSystemPermissionService.checkPermissionExist(menuV.getName(), menuV.getPermission());
            AdminPermissionV permissionV = new AdminPermissionV();
            permissionV.setPermissionUrl(menuV.getUrl());
            permissionV.setName(menuV.getName());
            permissionV.setPermission(menuV.getPermission());
            permissionV.setDescription(SystemMenuServiceImpl.MENU_PERMISSION_DESCRIPTION_STR);
            if (!check) {
                iSystemPermissionService.addPermission(permissionV);
            }
        }
    }

    @Autowired
    ISystemPermissionService iSystemPermissionService;
    @Autowired
    ISystemMenuService iSystemMenuService;
}
