package model;

import io.swagger.annotations.ApiModel;
import model.utils.SystemConstCode;

@ApiModel
public class Result<T> {
    public Result() {
    }

    private String msg;

    private String code;

    private T data;

    private boolean flag;

    public Result(String errMsg, String errCode, T data, boolean flg) {
        super();
        this.msg = errMsg;
        this.code = errCode;
        this.data = data;
        this.flag = flg;
    }

    public Result(T data) {
        super();
        this.msg = null;
        this.code = "200";
        this.data = data;
        this.flag = true;
    }

    public static <T> Result ok(T data) {
        return new Result("请求处理完成", "200", data, true);
    }

    public static <T> Result ok(int code, String errorMsg, T data) {
        return new Result(errorMsg, String.valueOf(code), data, true);
    }

    public static Result error(int errorCode, String errorMsg) {
        return new Result(errorMsg, String.valueOf(errorCode), null, false);
    }

    public static Result error(Integer errorCode, String errorMsg) {
        return new Result(errorMsg, String.valueOf(errorCode), null, false);
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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