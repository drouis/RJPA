package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ampq_message", schema = "system", catalog = "")
public class AmpqMessageEntity {
    private int id;
    private String uuid;
    private String ampqClass;
    private String ampqMemo;
    private String ampqQueName;
    private Integer ampqStatue;
    private Timestamp ampqTime;
    private Integer ampqType;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uuid", nullable = true, length = 255)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "ampq_class", nullable = true, length = 255)
    public String getAmpqClass() {
        return ampqClass;
    }

    public void setAmpqClass(String ampqClass) {
        this.ampqClass = ampqClass;
    }

    @Basic
    @Column(name = "ampq_memo", nullable = true, length = 255)
    public String getAmpqMemo() {
        return ampqMemo;
    }

    public void setAmpqMemo(String ampqMemo) {
        this.ampqMemo = ampqMemo;
    }

    @Basic
    @Column(name = "ampq_que_name", nullable = true, length = 255)
    public String getAmpqQueName() {
        return ampqQueName;
    }

    public void setAmpqQueName(String ampqQueName) {
        this.ampqQueName = ampqQueName;
    }

    @Basic
    @Column(name = "ampq_statue", nullable = true)
    public Integer getAmpqStatue() {
        return ampqStatue;
    }

    public void setAmpqStatue(Integer ampqStatue) {
        this.ampqStatue = ampqStatue;
    }

    @Basic
    @Column(name = "ampq_time", nullable = true)
    public Timestamp getAmpqTime() {
        return ampqTime;
    }

    public void setAmpqTime(Timestamp ampqTime) {
        this.ampqTime = ampqTime;
    }

    @Basic
    @Column(name = "ampq_type", nullable = true)
    public Integer getAmpqType() {
        return ampqType;
    }

    public void setAmpqType(Integer ampqType) {
        this.ampqType = ampqType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmpqMessageEntity that = (AmpqMessageEntity) o;
        return id == that.id &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(ampqClass, that.ampqClass) &&
                Objects.equals(ampqMemo, that.ampqMemo) &&
                Objects.equals(ampqQueName, that.ampqQueName) &&
                Objects.equals(ampqStatue, that.ampqStatue) &&
                Objects.equals(ampqTime, that.ampqTime) &&
                Objects.equals(ampqType, that.ampqType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uuid, ampqClass, ampqMemo, ampqQueName, ampqStatue, ampqTime, ampqType);
    }
}
