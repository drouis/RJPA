package com.rjpa.repository.Entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Proxy(lazy = false)
@Entity
@Table(name = "ampq_message", schema = "driverschool", catalog = "")
public class AmpqMessageEntity implements Serializable {
    private static final long serialVersionUID = 9166353003382453741L;
    private int id;
    private String uuid;
    private Integer ampqType;
    private String ampqQueName;
    private String ampqClass;
    private String ampqMemo;
    private Integer ampqStatue;
    private Timestamp ampqTIme;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "ampqType")
    public Integer getAmpqType() {
        return ampqType;
    }

    public void setAmpqType(Integer ampqType) {
        this.ampqType = ampqType;
    }

    @Basic
    @Column(name = "ampqQueName")
    public String getAmpqQueName() {
        return ampqQueName;
    }

    public void setAmpqQueName(String ampqQueName) {
        this.ampqQueName = ampqQueName;
    }

    @Basic
    @Column(name = "ampqClass")
    public String getAmpqClass() {
        return ampqClass;
    }

    public void setAmpqClass(String ampqClass) {
        this.ampqClass = ampqClass;
    }

    @Basic
    @Column(name = "ampqMemo")
    public String getAmpqMemo() {
        return ampqMemo;
    }

    public void setAmpqMemo(String ampqMemo) {
        this.ampqMemo = ampqMemo;
    }

    @Basic
    @Column(name = "ampqStatue")
    public Integer getAmpqStatue() {
        return ampqStatue;
    }

    public void setAmpqStatue(Integer ampqStatue) {
        this.ampqStatue = ampqStatue;
    }

    @Basic
    @Column(name = "ampqTIme")
    public Timestamp getAmpqTIme() {
        return ampqTIme;
    }

    public void setAmpqTIme(Timestamp ampqTIme) {
        this.ampqTIme = ampqTIme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmpqMessageEntity that = (AmpqMessageEntity) o;
        return id == that.id &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(ampqType, that.ampqType) &&
                Objects.equals(ampqQueName, that.ampqQueName) &&
                Objects.equals(ampqClass, that.ampqClass) &&
                Objects.equals(ampqMemo, that.ampqMemo) &&
                Objects.equals(ampqStatue, that.ampqStatue) &&
                Objects.equals(ampqTIme, that.ampqTIme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, ampqType, ampqQueName, ampqClass, ampqMemo, ampqStatue, ampqTIme);
    }
}
