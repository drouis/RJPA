package com.rjpa.repository.Entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Proxy(lazy = false)
@Entity
@Table(name = "lzh_admin_user_role", schema = "driverschool", catalog = "")
public class LzhAdminUserRoleEntity implements Serializable {
    private static final long serialVersionUID = -9120609032109835739L;
    private int id;
    private long userId;
    private long roleId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "role_id")
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LzhAdminUserRoleEntity that = (LzhAdminUserRoleEntity) o;
        return id == that.id &&
                userId == that.userId &&
                roleId == that.roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, roleId);
    }
}
