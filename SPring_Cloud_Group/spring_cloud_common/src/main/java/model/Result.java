package model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import model.utils.SystemConstCode;

@ApiModel
public class Result<T> {
    public Result() {
    }

    @ApiModelProperty(value = "通知")
    private String msg;
    @ApiModelProperty(value = "code")
    private Integer code;
    @ApiModelProperty(value = "对象")
    private T data;

    private boolean flag;

    public Result(String errMsg, Integer errCode, T data, boolean flg) {
        super();
        this.msg = errMsg;
        this.code = errCode;
        this.data = data;
        this.flag = flg;
    }

    public Result(T data) {
        super();
        this.msg = null;
        this.code = SystemConstCode.SUCCESS.getCode();
        this.data = data;
        this.flag = true;
    }

    public static <T> Result ok(T data) {
        return new Result("请求处理完成", SystemConstCode.SUCCESS.getCode(), data, true);
    }

    public static <T> Result ok(int code, String errorMsg, T data) {
        return new Result(errorMsg, code, data, true);
    }

    public static Result error(int errorCode, String errorMsg) {
        return new Result(errorMsg, errorCode, null, false);
    }

    public static Result error(Integer errorCode, String errorMsg) {
        return new Result(errorMsg, errorCode, null, false);
    }

    public static Result error(String errMsg) {
        return error(SystemConstCode.END_STR.getCode(), errMsg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                ", flag=" + flag +
                '}';
    }
}