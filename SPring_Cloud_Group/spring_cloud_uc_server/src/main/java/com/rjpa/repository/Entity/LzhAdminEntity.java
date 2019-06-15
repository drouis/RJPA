package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "lzh_admin", schema = "system", catalog = "")
public class LzhAdminEntity {
    private int id;
    private String userUuid;
    private String userName;
    private String phoneNumber;
    private String userPassword;
    private String realName;
    private String headPicture;
    private String brithday;
    private Integer age;
    private Integer sex;
    private Integer agentFlg;
    private Integer verifyFlg;
    private Integer state;
    private Date addDate;
    private Date updateDate;
    private String shopName;
    private String shopOwner;
    private String customAddress;
    private String shopNum;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_uuid", nullable = true, length = 32)
    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 20)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "phone_number", nullable = true, length = 20)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "real_name", nullable = true, length = 20)
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Basic
    @Column(name = "head_picture", nullable = true, length = 500)
    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    @Basic
    @Column(name = "update_date", nullable = true)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "add_date", nullable = true)
    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    @Basic
    @Column(name = "brithday", nullable = true, length = 11)
    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    @Basic
    @Column(name = "age", nullable = true)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "sex", nullable = true)
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "verify_flg", nullable = true)
    public Integer getVerifyFlg() {
        return verifyFlg;
    }

    public void setVerifyFlg(Integer verifyFlg) {
        this.verifyFlg = verifyFlg;
    }

    @Basic
    @Column(name = "agent_flg", nullable = true)
    public Integer getAgentFlg() {
        return agentFlg;
    }

    public void setAgentFlg(Integer agentFlg) {
        this.agentFlg = agentFlg;
    }

    @Basic
    @Column(name = "user_password", nullable = true, length = 255)
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Column(name = "state", nullable = true, length = 255)
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LzhAdminEntity that = (LzhAdminEntity) o;
        return id == that.id &&
                Objects.equals(userUuid, that.userUuid) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(realName, that.realName) &&
                Objects.equals(headPicture, that.headPicture) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(addDate, that.addDate) &&
                Objects.equals(brithday, that.brithday) &&
                Objects.equals(age, that.age) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(verifyFlg, that.verifyFlg) &&
                Objects.equals(agentFlg, that.agentFlg);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userUuid, userName, phoneNumber, realName, headPicture, updateDate, addDate, brithday, age, sex, verifyFlg, agentFlg);
    }

    @Basic
    @Column(name = "shop_name", nullable = true, length = 100)
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Basic
    @Column(name = "shop_owner", nullable = true, length = 100)
    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    @Basic
    @Column(name = "custom_address", nullable = true, length = 100)
    public String getCustomAddress() {
        return customAddress;
    }

    public void setCustomAddress(String customAddress) {
        this.customAddress = customAddress;
    }

    @Basic
    @Column(name = "shop_num", nullable = true, length = 50)
    public String getShopNum() {
        return shopNum;
    }

    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }
}
