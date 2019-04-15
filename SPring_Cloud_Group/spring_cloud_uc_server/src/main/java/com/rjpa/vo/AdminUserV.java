package com.rjpa.vo;

import com.rjpa.repository.Entity.LzhAdminEntity;

import java.io.Serializable;
import java.util.List;

public class AdminUserV extends LzhAdminEntity implements Serializable {
    List allowPermissionService;

    String roleNameStr;

    public String getRoleNameStr() {
        return roleNameStr;
    }

    public void setRoleNameStr(String roleNameStr) {
        this.roleNameStr = roleNameStr;
    }

    public List getAllowPermissionService() {
        return allowPermissionService;
    }

    public void setAllowPermissionService(List allowPermissionService) {
        this.allowPermissionService = allowPermissionService;
    }
}
