package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sys_dictionary_data", schema = "system", catalog = "")
public class SysDictionaryDataEntity {
    private long id;
    private long parentId;
    private Long groupId;
    private String groupCode;
    private String dicCode;
    private String dicName;
    private String remark;
    private String path;
    private Integer sort;
    private String orgId;
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
    @Column(name = "parent_id", nullable = false)
    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "group_id", nullable = true)
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
    @Column(name = "dic_code", nullable = false, length = 50)
    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    @Basic
    @Column(name = "dic_name", nullable = false, length = 20)
    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "path", nullable = false, length = 255)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "sort", nullable = true)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "org_id", nullable = true, length = 64)
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
        SysDictionaryDataEntity that = (SysDictionaryDataEntity) o;
        return id == that.id &&
                parentId == that.parentId &&
                disable == that.disable &&
                deleteFlag == that.deleteFlag &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(groupCode, that.groupCode) &&
                Objects.equals(dicCode, that.dicCode) &&
                Objects.equals(dicName, that.dicName) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(path, that.path) &&
                Objects.equals(sort, that.sort) &&
                Objects.equals(orgId, that.orgId) &&
                Objects.equals(createdUser, that.createdUser) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedUser, that.updatedUser) &&
                Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, parentId, groupId, groupCode, dicCode, dicName, remark, path, sort, orgId, createdUser, createdAt, updatedUser, lastUpdated, disable, deleteFlag);
    }
}
