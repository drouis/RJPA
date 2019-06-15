package Entity;

import java.io.Serializable;
import java.util.List;

public class AdminUserV extends LzhAdminEntityV implements Serializable {

    private static final long serialVersionUID = 5688891076801221044L;
    List allowPermissionService;

    String roleNameStr;

    List<IndexPageMenuV> menuVS;

    boolean adminFlg=false;

    public List<IndexPageMenuV> getMenuVS() {
        return menuVS;
    }

    public void setMenuVS(List<IndexPageMenuV> menuVS) {
        this.menuVS = menuVS;
    }

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

    public boolean isAdminFlg() {
        return adminFlg;
    }

    public void setAdminFlg(boolean adminFlg) {
        this.adminFlg = adminFlg;
    }
}
