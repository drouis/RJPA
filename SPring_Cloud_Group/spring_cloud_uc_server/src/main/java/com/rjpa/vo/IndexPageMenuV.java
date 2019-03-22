package com.rjpa.vo;

import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;

import java.util.List;

public class IndexPageMenuV extends LzhAdminMenusRightsEntity {
    List<LzhAdminMenusRightsEntity> subMenus;

    public List<LzhAdminMenusRightsEntity> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<LzhAdminMenusRightsEntity> subMenus) {
        this.subMenus = subMenus;
    }
}
