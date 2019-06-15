package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "lzh_admin_inmail", schema = "system", catalog = "")
public class LzhAdminInmailEntity {
    private int id;
    private String inMessageUuid;
    private String inMessageTitle;
    private Timestamp inMessageSystemtime;
    private Integer inMessageReadflg;
    private String inMessageOwner;
    private String inMessageMemo;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "in_message_uuid", nullable = true, length = 32)
    public String getInMessageUuid() {
        return inMessageUuid;
    }

    public void setInMessageUuid(String inMessageUuid) {
        this.inMessageUuid = inMessageUuid;
    }

    @Basic
    @Column(name = "in_message_title", nullable = true, length = 100)
    public String getInMessageTitle() {
        return inMessageTitle;
    }

    public void setInMessageTitle(String inMessageTitle) {
        this.inMessageTitle = inMessageTitle;
    }

    @Basic
    @Column(name = "in_message_systemtime", nullable = true)
    public Timestamp getInMessageSystemtime() {
        return inMessageSystemtime;
    }

    public void setInMessageSystemtime(Timestamp inMessageSystemtime) {
        this.inMessageSystemtime = inMessageSystemtime;
    }

    @Basic
    @Column(name = "in_message_readflg", nullable = true)
    public Integer getInMessageReadflg() {
        return inMessageReadflg;
    }

    public void setInMessageReadflg(Integer inMessageReadflg) {
        this.inMessageReadflg = inMessageReadflg;
    }

    @Basic
    @Column(name = "in_message_owner", nullable = true, length = 32)
    public String getInMessageOwner() {
        return inMessageOwner;
    }

    public void setInMessageOwner(String inMessageOwner) {
        this.inMessageOwner = inMessageOwner;
    }

    @Basic
    @Column(name = "in_message_memo", nullable = true, length = 500)
    public String getInMessageMemo() {
        return inMessageMemo;
    }

    public void setInMessageMemo(String inMessageMemo) {
        this.inMessageMemo = inMessageMemo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LzhAdminInmailEntity that = (LzhAdminInmailEntity) o;
        return id == that.id &&
                Objects.equals(inMessageUuid, that.inMessageUuid) &&
                Objects.equals(inMessageTitle, that.inMessageTitle) &&
                Objects.equals(inMessageSystemtime, that.inMessageSystemtime) &&
                Objects.equals(inMessageReadflg, that.inMessageReadflg) &&
                Objects.equals(inMessageOwner, that.inMessageOwner) &&
                Objects.equals(inMessageMemo, that.inMessageMemo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, inMessageUuid, inMessageTitle, inMessageSystemtime, inMessageReadflg, inMessageOwner, inMessageMemo);
    }
}
