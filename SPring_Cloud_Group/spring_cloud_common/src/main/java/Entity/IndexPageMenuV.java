package Entity;

import java.io.Serializable;
import java.util.List;

public class IndexPageMenuV extends LzhAdminMenusRightsEntityV implements Serializable {

    private static final long serialVersionUID = -5515679229644786292L;
    String selected = "";

    List<LzhAdminMenusRightsEntityV> subMenus;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public List<LzhAdminMenusRightsEntityV> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<LzhAdminMenusRightsEntityV> subMenus) {
        this.subMenus = subMenus;
    }
}
