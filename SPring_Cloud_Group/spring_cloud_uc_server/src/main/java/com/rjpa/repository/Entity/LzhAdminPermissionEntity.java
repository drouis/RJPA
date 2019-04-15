package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "lzh_admin_permission", schema = "driverschool", catalog = "")
public class LzhAdminPermissionEntity {
    private int id;
    private String name;
    private String permission;
    private String permissionUrl;
    private String description;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "permission")
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Basic
    @Column(name = "permissionUrl")
    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    @Basic
    @Column(name = "description")
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
                Objects.equals(permissionUrl, that.permissionUrl) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, permission, permissionUrl, description);
    }
}
