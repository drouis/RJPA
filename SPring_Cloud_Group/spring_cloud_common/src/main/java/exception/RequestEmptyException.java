package exception;

public class RequestEmptyException extends ServiceException  {
    public RequestEmptyException() {
        super(400, "请求数据不完整或格式错误！");
    }

    public RequestEmptyException(String errorMessage) {
        super(400, errorMessage);
    }

    public synchronized Throwable fillInStackTrace() {
        return null;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RequestEmptyException)) {
            return false;
        } else {
            RequestEmptyException other = (RequestEmptyException)o;
            return other.canEqual(this);
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RequestEmptyException;
    }

    public int hashCode() {
        int result = 1;
        return result;
    }

    public String toString() {
        return "RequestEmptyException()";
    }
}
