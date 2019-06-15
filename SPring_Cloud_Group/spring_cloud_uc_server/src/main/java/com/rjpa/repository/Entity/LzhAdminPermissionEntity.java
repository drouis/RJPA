package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "lzh_admin_permission", schema = "system", catalog = "")
public class LzhAdminPermissionEntity {
    private long id;
    private String name;
    private String permission;
    private String description;
    private String permissionUrl;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "permission", nullable = true, length = 255)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LzhAdminPermissionEntity that = (LzhAdminPermissionEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(permission, that.permission) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, permission, description);
    }

    @Basic
    @Column(name = "permission_url", nullable = true, length = 255)
    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }
}
