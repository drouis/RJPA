package model.utils;

public enum SystemConstCode {
    SUCCESS(200, "系统执行成功。"),
    ERROR(204, "系统发生不可预知的异常错误。"),
    PARA_ERROR(300, "API接口参数错误。"),
    PARA_EMPTY_ERROR(300, "{paramName}不能为空。"),
    //    用户错误信息
    USER_USERNAME_ERROR(2010, "用户名称错误。"),
    USER_USERPWD_ERROR(2011, "用户密码错误。"),
    USER_NOT_FOUND(2012, "用户查询失败。"),
    USER_IS_EXIST(2013, "用户已经存在。"),
    USER_USERNAME_IS_EXIST(2014, "登陆名已经存在。"),
    USER_PHONENUMBER_IS_EXIST(2015, "登陆手机号已经绑定。"),
    USER_LOGIN_TIMEOUT(2016, "长时间未操作，登陆超时。"),
    USER_LOIGN_TOKEN_EMPTY(2017,"用户登陆验证失效，请重新登陆"),
    // 系统菜单配置错误
    MENU_NAME_ERROR(2020, "菜单名称错误。"),
    MENU_INSERT_FAILD_ERROR(2021, "菜单创建失败。"),
    MENU_UPDATE_FAILD_ERROR(2022, "菜单修改失败。"),
    MENU_DELETE_FAILD_ERROR(2023, "菜单删除失败。"),
    MENU_NOT_FOUND(2024, "菜单查询失败。"),
    MENU_IS_EXIST(2025, "菜单已经存在。"),
    MENU_SUBMENUS_EXIST_ERROR(2026, "子菜单已经存在，无法处理数据请求。"),
    // 系统权限配置错误
    USERRIGNT_NAME_ERROR(2030, "系统访问权限名称错误。"),
    USERRIGNT_INSERT_FAILD_ERROR(2031, "系统访问权限创建失败。"),
    USERRIGNT_UPDATE_FAILD_ERROR(2032, "系统访问权限修改失败。"),
    USERRIGNT_DELETE_FAILD_ERROR(2033, "系统访问权限删除失败。"),
    USERRIGNT_NOT_FOUND(2034, "系统访问权限查询失败。"),
    USERRIGNT_IS_EXIST(2035, "系统访问权限已经存在。"),

    // 系统权限配置错误
    USERROLE_NAME_ERROR(2040, "用户角色名称错误。"),
    USERROLE_INSERT_FAILD_ERROR(2041, "用户角色创建失败。"),
    USERROLE_UPDATE_FAILD_ERROR(2042, "用户角色修改失败。"),
    USERROLE_DELETE_FAILD_ERROR(2043, "用户角色删除失败。"),
    USERROLE_NOT_FOUND(2044, "用户角色查询失败。"),
    USERROLE_IS_EXIST(2045, "用户角色已经存在。"),

    // 系统消息配置错误
    MESSAGE_HISTORYDATA_NOT_EXIST(3000, "系统消息未发送成功，没有发送历史数据"),
    MESSAGE_HISTORYDATA_UP_FAILD(3099, "系统消息更新历史数据失败"),

    // 车场数据错误
    CAR_GROUND_DATA_NOT_EXIST(4003001,"车场数据不存在"),
    CAR_GROUND_DATA_IS_EXIST(4003002,"车场数据重复存在"),
    CAR_GROUND_DATA_IS_USING(4003003,"车场数据正在使用无法修改数据"),
    CAR_GROUND_DATA_INSERT_FAILD_ERROR(4003004,"车场数据创建失败"),
    CAR_GROUND_DATA_UPDATE_FAILD_ERROR(4003005,"车场数据修改失败"),
    CAR_GROUND_DATA_DELETE_FAILD_ERROR(4003006,"车场数据删除失败"),

    ORDER_DATA_INSERT_FAILD_ERROR(400404,"订单数据创建失败！"),
    ORDER_DATA_UDDATE_FAILD_ERROR(400405,"订单数据更新失败！"),
    ORDER_DATA_DELETE_FAILD_ERROR(400406,"订单数据删除失败！"),


    // 应用端用户信息错误
    MESSAGE_SMS_VERIFY_UNSAME(2001,"用户登陆短信验证错误"),
    MESSAGE_SMS_VERIFY_EMPTY(2002,"请输入登陆验证码"),
    MESSAGE_SMS_VERIFY_PHONENUMBER_EMPTY(2003,"请填写发送短信手机号"),
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