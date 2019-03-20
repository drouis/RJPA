package model.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class LzhAdminEntity implements Serializable {

    private int id;
    private String userName;
    private String password;
    private String realName;
    private Integer age;
    private String phoneNumber;
    private String headPicture;
    private Date addDate;
    private Date updateDate;
    private Integer state;
    List allowPermissionService;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List getAllowPermissionService() {
        return allowPermissionService;
    }

    public void setAllowPermissionService(List allowPermissionService) {
        this.allowPermissionService = allowPermissionService;
    }
}