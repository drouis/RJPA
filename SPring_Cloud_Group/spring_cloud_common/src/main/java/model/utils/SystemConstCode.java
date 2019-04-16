package model.utils;

public enum SystemConstCode {
    SUCCESS(200, "系统执行成功。"),
    ERROR(204, "系统发生不可预知的异常错误。"),
    PARA_ERROR(300, "API接口参数错误。"),

    //    用户错误信息
    USER_USERNAME_ERROR(2001, "用户名称错误。"),
    USER_USERPWD_ERROR(2002, "用户密码错误。"),
    USER_NOT_FOUND(2003, "用户查询失败。"),
    USER_IS_EXIST(2004, "用户已经存在。"),
    USER_USERNAME_IS_EXIST(2004, "登陆名已经存在。"),
    USER_PHONENUMBER_IS_EXIST(2004, "登陆手机号已经绑定。"),
    USER_LOGIN_TIMEOUT(2099, "长时间未操作，登陆超时。"),
    // 系统菜单配置错误
    MENU_NAME_ERROR(3001, "菜单名称错误。"),
    MENU_INSERT_FAILD_ERROR(3002, "菜单创建失败。"),
    MENU_UPDATE_FAILD_ERROR(3002, "菜单修改失败。"),
    MENU_DELETE_FAILD_ERROR(3002, "菜单删除失败。"),
    MENU_NOT_FOUND(3003, "菜单查询失败。"),
    MENU_IS_EXIST(3004, "菜单已经存在。"),
    MENU_SUBMENUS_EXIST_ERROR(3005, "子菜单已经存在，无法处理数据请求。"),
    // 系统权限配置错误
    USERRIGNT_NAME_ERROR(4001, "系统访问权限名称错误。"),
    USERRIGNT_INSERT_FAILD_ERROR(4002, "系统访问权限创建失败。"),
    USERRIGNT_UPDATE_FAILD_ERROR(4002, "系统访问权限修改失败。"),
    USERRIGNT_DELETE_FAILD_ERROR(4002, "系统访问权限删除失败。"),
    USERRIGNT_NOT_FOUND(4003, "系统访问权限查询失败。"),
    USERRIGNT_IS_EXIST(4004, "系统访问权限已经存在。"),

    // 系统权限配置错误
    USERROLE_NAME_ERROR(4001, "用户角色名称错误。"),
    USERROLE_INSERT_FAILD_ERROR(4002, "用户角色创建失败。"),
    USERROLE_UPDATE_FAILD_ERROR(4002, "用户角色修改失败。"),
    USERROLE_DELETE_FAILD_ERROR(4002, "用户角色删除失败。"),
    USERROLE_NOT_FOUND(4003, "用户角色查询失败。"),
    USERROLE_IS_EXIST(4004, "用户角色已经存在。"),
    END_STR(999, "");

    SystemConstCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}