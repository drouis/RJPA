package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "lzh_admin_login_log", schema = "system", catalog = "")
public class LzhAdminLoginLogEntity {
    private int id;
    private Integer logincount;
    private String loginname;
    private Timestamp logintime;
    private String memo1;
    private String memo2;
    private String memo3;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "logincount", nullable = true)
    public Integer getLogincount() {
        return logincount;
    }

    public void setLogincount(Integer logincount) {
        this.logincount = logincount;
    }

    @Basic
    @Column(name = "loginname", nullable = true, length = 255)
    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    @Basic
    @Column(name = "logintime", nullable = true)
    public Timestamp getLogintime() {
        return logintime;
    }

    public void setLogintime(Timestamp logintime) {
        this.logintime = logintime;
    }

    @Basic
    @Column(name = "memo1", nullable = true, length = 255)
    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    @Basic
    @Column(name = "memo2", nullable = true, length = 255)
    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    @Basic
    @Column(name = "memo3", nullable = true, length = 255)
    public String getMemo3() {
        return memo3;
    }

    public void setMemo3(String memo3) {
        this.memo3 = memo3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LzhAdminLoginLogEntity that = (LzhAdminLoginLogEntity) o;
        return id == that.id &&
                Objects.equals(logincount, that.logincount) &&
                Objects.equals(loginname, that.loginname) &&
                Objects.equals(logintime, that.logintime) &&
                Objects.equals(memo1, that.memo1) &&
                Objects.equals(memo2, that.memo2) &&
                Objects.equals(memo3, that.memo3);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, logincount, loginname, logintime, memo1, memo2, memo3);
    }
}
