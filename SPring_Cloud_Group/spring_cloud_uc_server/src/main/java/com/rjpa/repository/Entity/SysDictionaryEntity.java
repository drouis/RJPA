package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sys_dictionary", schema = "system", catalog = "")
public class SysDictionaryEntity {
    private String id;
    private String orgId;
    private Timestamp createdAt;
    private Timestamp lastUpdated;
    private String createdUser;
    private String updatedUser;
    private Boolean disable;
    private boolean deleteFlag;
    private String dicKey;
    private String dicValue;
    private String parentId;
    private String levelId;
    private String path;
    private String remark;
    private Integer sort;
    private String type;

    @Id
    @Column(name = "id", nullable = false, length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "created_at", nullable = true)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "last_updated", nullable = true)
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Basic
    @Column(name = "created_user", nullable = true, length = 64)
    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    @Basic
    @Column(name = "updated_user", nullable = true, length = 64)
    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    @Basic
    @Column(name = "disable", nullable = true)
    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
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

    @Basic
    @Column(name = "dic_key", nullable = false, length = 50)
    public String getDicKey() {
        return dicKey;
    }

    public void setDicKey(String dicKey) {
        this.dicKey = dicKey;
    }

    @Basic
    @Column(name = "dic_value", nullable = false, length = 255)
    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

    @Basic
    @Column(name = "parent_id", nullable = true, length = 64)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "level_id", nullable = true, length = 64)
    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    @Basic
    @Column(name = "path", nullable = true, length = 255)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
    @Column(name = "sort", nullable = true)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysDictionaryEntity that = (SysDictionaryEntity) o;
        return deleteFlag == that.deleteFlag &&
                Objects.equals(id, that.id) &&
                Objects.equals(orgId, that.orgId) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(lastUpdated, that.lastUpdated) &&
                Objects.equals(createdUser, that.createdUser) &&
                Objects.equals(updatedUser, that.updatedUser) &&
                Objects.equals(disable, that.disable) &&
                Objects.equals(dicKey, that.dicKey) &&
                Objects.equals(dicValue, that.dicValue) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(levelId, that.levelId) &&
                Objects.equals(path, that.path) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(sort, that.sort) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orgId, createdAt, lastUpdated, createdUser, updatedUser, disable, deleteFlag, dicKey, dicValue, parentId, levelId, path, remark, sort, type);
    }
}
