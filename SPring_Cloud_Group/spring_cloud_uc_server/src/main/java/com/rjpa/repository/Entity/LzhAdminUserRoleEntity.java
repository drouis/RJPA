package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "lzh_admin_user_role", schema = "system", catalog = "")
public class LzhAdminUserRoleEntity {
    private long id;
    private long userId;
    private long roleId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "role_id", nullable = false)
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
