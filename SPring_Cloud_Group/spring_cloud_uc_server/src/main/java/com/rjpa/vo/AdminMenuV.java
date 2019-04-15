package com.rjpa.vo;

import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;

import java.util.List;

public class AdminMenuV extends LzhAdminMenusRightsEntity {
    String parentMenuName;

    List<LzhAdminMenusRightsEntity> subMenus;

    int bundMenuSelect;

    public int getBundMenuSelect() {
        return bundMenuSelect;
    }

    public void setBundMenuSelect(int bundMenuSelect) {
        this.bundMenuSelect = bundMenuSelect;
    }

    public String getParentMenuName() {
        return parentMenuName;
    }

    public void setParentMenuName(String parentMenuName) {
        this.parentMenuName = parentMenuName;
    }

    public List<LzhAdminMenusRightsEntity> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<LzhAdminMenusRightsEntity> subMenus) {
        this.subMenus = subMenus;
    }
}
