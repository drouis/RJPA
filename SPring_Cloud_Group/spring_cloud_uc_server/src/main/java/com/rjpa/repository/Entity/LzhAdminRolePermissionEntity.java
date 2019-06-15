package com.rjpa.repository.Entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

@Proxy(lazy = false)
@Entity
@Table(name = "lzh_admin_role_permission", schema = "system", catalog = "")
public class LzhAdminRolePermissionEntity implements Serializable {
    private static final long serialVersionUID = 3689352352578382335L;
    private long id;
    private Long roleId;
    private Long permissionId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role_id", nullable = true)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "permission_id", nullable = true)
    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LzhAdminRolePermissionEntity that = (LzhAdminRolePermissionEntity) o;

        if (id != that.id) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (permissionId != null ? !permissionId.equals(that.permissionId) : that.permissionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (permissionId != null ? permissionId.hashCode() : 0);
        return result;
    }
}
