package com.rjpa.repository.Entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Proxy(lazy = false)
@Entity
@Table(name = "sys_dictcode", schema = "driverschool", catalog = "")
public class SysDictcodeEntity implements Serializable {
    private static final long serialVersionUID = 6261416212928654941L;
    private int did;
    private String dcolumn;
    private String dpresent;
    private Integer dvalue;
    private String dmemo;

    @Id
    @Column(name = "did")
    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    @Basic
    @Column(name = "dcolumn")
    public String getDcolumn() {
        return dcolumn;
    }

    public void setDcolumn(String dcolumn) {
        this.dcolumn = dcolumn;
    }

    @Basic
    @Column(name = "dpresent")
    public String getDpresent() {
        return dpresent;
    }

    public void setDpresent(String dpresent) {
        this.dpresent = dpresent;
    }

    @Basic
    @Column(name = "dvalue")
    public Integer getDvalue() {
        return dvalue;
    }

    public void setDvalue(Integer dvalue) {
        this.dvalue = dvalue;
    }

    @Basic
    @Column(name = "dmemo")
    public String getDmemo() {
        return dmemo;
    }

    public void setDmemo(String dmemo) {
        this.dmemo = dmemo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysDictcodeEntity that = (SysDictcodeEntity) o;
        return did == that.did &&
                Objects.equals(dcolumn, that.dcolumn) &&
                Objects.equals(dpresent, that.dpresent) &&
                Objects.equals(dvalue, that.dvalue) &&
                Objects.equals(dmemo, that.dmemo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(did, dcolumn, dpresent, dvalue, dmemo);
    }
}
