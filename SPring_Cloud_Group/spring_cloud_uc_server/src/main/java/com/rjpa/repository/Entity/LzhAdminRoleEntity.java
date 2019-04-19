package com.rjpa.repository.Entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Proxy(lazy = false)
@Entity
@Table(name = "lzh_admin_role", schema = "driverschool", catalog = "")
public class LzhAdminRoleEntity implements Serializable {
    private static final long serialVersionUID = -3137499071341748837L;
    private int id;
    private String name;
    private String role;
    private String description;
    private Integer flg;

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
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "flg")
    public Integer getFlg() {
        return flg;
    }

    public void setFlg(Integer flg) {
        this.flg = flg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LzhAdminRoleEntity that = (LzhAdminRoleEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(role, that.role) &&
                Objects.equals(description, that.description) &&
                Objects.equals(flg, that.flg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, role, description, flg);
    }
}
