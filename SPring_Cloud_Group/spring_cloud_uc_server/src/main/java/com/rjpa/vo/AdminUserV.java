package com.rjpa.vo;

import Entity.LzhAdminEntityV;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@ApiModel(value = "AdminUserV", description = "系统用户信息 && 经纪人信息 && 商户信息")
public class AdminUserV extends LzhAdminEntityV implements Serializable {
    private static final long serialVersionUID = -641916441499345625L;
    @ApiModelProperty(value = "id 用户ID")
    private int id;
    @ApiModelProperty(value = "userName 昵称")
    private String userName;
    @ApiModelProperty(value = "password 登陆密码")
    private String password;
    @ApiModelProperty(value = "realName 真实姓名")
    private String realName;
    @ApiModelProperty(value = "age 年龄")
    private Integer age;
    @ApiModelProperty(value = "phoneNumber 绑定手机")
    private String phoneNumber;
    @ApiModelProperty(value = "headPicture 头像")
    private String headPicture;
    @ApiModelProperty(value = "addDate 添加系统时间")
    private Date addDate;
    @ApiModelProperty(value = "updateDate 更新系统时间")
    private Date updateDate;
    @ApiModelProperty(value = "state 用户状态")
    private Integer state;
    @ApiModelProperty(value = "sex 性别")
    private Integer sex;
    @ApiModelProperty(value = "verifyFlg 实名验证区分")
    private Integer verifyFlg;
    @ApiModelProperty(value = "allowPermissionService 用户服务访问权限里列表")
    List allowPermissionService;
    @ApiModelProperty(value = "roleNameStr 用户访问权限名称")
    String roleNameStr;
    @ApiModelProperty(value = "menuVS 用户访问菜单列表")
    List<IndexPageMenuV> menuVS;
    @ApiModelProperty(value = "adminFlg 管理员区分")
    boolean adminFlg = false;
    @ApiModelProperty(value = "brithday 出生年月")
    private String brithday;
    @ApiModelProperty(value = "agentFlg 用户身份识别")
    private Integer agentFlg;
    @ApiModelProperty(value = "shopName 企业名称")
    private String shopName;
    @ApiModelProperty(value = "shopOwner 企业法人")
    private String shopOwner;
    @ApiModelProperty(value = "customAddress 常在区域")
    private String customAddress;
    @ApiModelProperty(value = "shopNum 商户编号")
    private String shopNum;

    String loginTokenStr;

    public static int getAgentUserFlg() {
        return 3;
    }

    public static int getShopUserFlg() {
        return 2;
    }

    public static int getAdminUserFlg() {
        return 1;
    }

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getVerifyFlg() {
        return verifyFlg;
    }

    public void setVerifyFlg(Integer verifyFlg) {
        this.verifyFlg = verifyFlg;
    }

    public List getAllowPermissionService() {
        return allowPermissionService;
    }

    public void setAllowPermissionService(List allowPermissionService) {
        this.allowPermissionService = allowPermissionService;
    }

    public String getRoleNameStr() {
        return roleNameStr;
    }

    public void setRoleNameStr(String roleNameStr) {
        this.roleNameStr = roleNameStr;
    }

    public List<IndexPageMenuV> getMenuVS() {
        return menuVS;
    }

    public void setMenuVS(List<IndexPageMenuV> menuVS) {
        this.menuVS = menuVS;
    }

    public boolean isAdminFlg() {
        return adminFlg;
    }

    public void setAdminFlg(boolean adminFlg) {
        this.adminFlg = adminFlg;
    }

    public String getLoginTokenStr() {
        return loginTokenStr;
    }

    public void setLoginTokenStr(String loginTokenStr) {
        this.loginTokenStr = loginTokenStr;
    }

    @Override
    public String getBrithday() {
        return brithday;
    }

    @Override
    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    @Override
    public Integer getAgentFlg() {
        return agentFlg;
    }

    @Override
    public void setAgentFlg(Integer agentFlg) {
        this.agentFlg = agentFlg;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public String getCustomAddress() {
        return customAddress;
    }

    public void setCustomAddress(String customAddress) {
        this.customAddress = customAddress;
    }

    public String getShopNum() {
        return shopNum;
    }

    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }
}
