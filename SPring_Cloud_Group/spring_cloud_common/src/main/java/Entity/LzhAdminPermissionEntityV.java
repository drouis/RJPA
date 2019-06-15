package Entity;

import java.io.Serializable;
import java.util.Objects;


public class LzhAdminPermissionEntityV implements Serializable {
    private static final long serialVersionUID = -5126379909324366507L;
    private int id;
    private String name;
    private String permission;
    private String permissionUrl;
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int hashCode() {
        return Objects.hash(id, name, permission, permissionUrl, description);
    }
}
