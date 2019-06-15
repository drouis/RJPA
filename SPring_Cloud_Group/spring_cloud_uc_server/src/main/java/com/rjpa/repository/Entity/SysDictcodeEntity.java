package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sys_dictcode", schema = "system", catalog = "")
public class SysDictcodeEntity {
    private int did;
    private String dcolumn;
    private String dmemo;
    private String dpresent;
    private Integer dvalue;

    @Id
    @Column(name = "did", nullable = false)
    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    @Basic
    @Column(name = "dcolumn", nullable = true, length = 255)
    public String getDcolumn() {
        return dcolumn;
    }

    public void setDcolumn(String dcolumn) {
        this.dcolumn = dcolumn;
    }

    @Basic
    @Column(name = "dmemo", nullable = true, length = 255)
    public String getDmemo() {
        return dmemo;
    }

    public void setDmemo(String dmemo) {
        this.dmemo = dmemo;
    }

    @Basic
    @Column(name = "dpresent", nullable = true, length = 255)
    public String getDpresent() {
        return dpresent;
    }

    public void setDpresent(String dpresent) {
        this.dpresent = dpresent;
    }

    @Basic
    @Column(name = "dvalue", nullable = true)
    public Integer getDvalue() {
        return dvalue;
    }

    public void setDvalue(Integer dvalue) {
        this.dvalue = dvalue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysDictcodeEntity that = (SysDictcodeEntity) o;
        return did == that.did &&
                Objects.equals(dcolumn, that.dcolumn) &&
                Objects.equals(dmemo, that.dmemo) &&
                Objects.equals(dpresent, that.dpresent) &&
                Objects.equals(dvalue, that.dvalue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(did, dcolumn, dmemo, dpresent, dvalue);
    }
}
