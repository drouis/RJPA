package com.rjpa.vo;

import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;

import java.util.List;

public class IndexPageMenuV extends LzhAdminMenusRightsEntity {
    String selected = "";
    List<LzhAdminMenusRightsEntity> subMenus;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public List<LzhAdminMenusRightsEntity> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<LzhAdminMenusRightsEntity> subMenus) {
        this.subMenus = subMenus;
    }
}
