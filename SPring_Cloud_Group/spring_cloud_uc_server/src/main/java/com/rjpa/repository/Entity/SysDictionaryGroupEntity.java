package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sys_dictionary_group", schema = "system", catalog = "")
public class SysDictionaryGroupEntity {
    private long id;
    private String groupCode;
    private String groupName;
    private String remark;
    private String createdUser;
    private Timestamp createdAt;
    private String updatedUser;
    private Timestamp lastUpdated;
    private boolean disable;
    private boolean deleteFlag;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_code", nullable = false, length = 50)
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Basic
    @Column(name = "group_name", nullable = false, length = 20)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 100)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "created_user", nullable = false, length = 64)
    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_user", nullable = false, length = 64)
    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    @Basic
    @Column(name = "last_updated", nullable = false)
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Basic
    @Column(name = "disable", nullable = false)
    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    @Basic
    @Column(name = "delete_flag", nullable = false)
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysDictionaryGroupEntity that = (SysDictionaryGroupEntity) o;
        return id == that.id &&
                disable == that.disable &&
                deleteFlag == that.deleteFlag &&
                Objects.equals(groupCode, that.groupCode) &&
                Objects.equals(groupName, that.groupName) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(createdUser, that.createdUser) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedUser, that.updatedUser) &&
                Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, groupCode, groupName, remark, createdUser, createdAt, updatedUser, lastUpdated, disable, deleteFlag);
    }
}
