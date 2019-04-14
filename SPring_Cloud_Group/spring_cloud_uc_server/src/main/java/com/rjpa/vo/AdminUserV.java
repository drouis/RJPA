package com.rjpa.vo;

import com.rjpa.repository.Entity.LzhAdminEntity;

import java.io.Serializable;

public class AdminUserV extends LzhAdminEntity implements Serializable {
    String roleNameStr;

    public String getRoleNameStr() {
        return roleNameStr;
    }

    public void setRoleNameStr(String roleNameStr) {
        this.roleNameStr = roleNameStr;
    }
}
