package Entity;

import java.io.Serializable;
import java.util.List;

public class IndexPageMenuV extends LzhAdminMenusRightsEntity implements Serializable {

    private static final long serialVersionUID = -5515679229644786292L;
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
