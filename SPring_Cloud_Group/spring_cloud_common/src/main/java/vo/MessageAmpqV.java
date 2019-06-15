package vo;

import java.io.Serializable;
import java.util.Date;

public class MessageAmpqV implements Serializable {
    private int id;
    private String uuid;
    private Integer ampqType;
    private String ampqQueName;
    private String ampqClass;
    private String ampqMemo;
    private Integer ampqStatue;
    private Date ampqtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getAmpqType() {
        return ampqType;
    }

    public void setAmpqType(Integer ampqType) {
        this.ampqType = ampqType;
    }

    public String getAmpqQueName() {
        return ampqQueName;
    }

    public void setAmpqQueName(String ampqQueName) {
        this.ampqQueName = ampqQueName;
    }

    public String getAmpqClass() {
        return ampqClass;
    }

    public void setAmpqClass(String ampqClass) {
        this.ampqClass = ampqClass;
    }

    public String getAmpqMemo() {
        return ampqMemo;
    }

    public void setAmpqMemo(String ampqMemo) {
        this.ampqMemo = ampqMemo;
    }

    public Integer getAmpqStatue() {
        return ampqStatue;
    }

    public void setAmpqStatue(Integer ampqStatue) {
        this.ampqStatue = ampqStatue;
    }

    public Date getAmpqtime() {
        return ampqtime;
    }

    public void setAmpqtime(Date ampqtime) {
        this.ampqtime = ampqtime;
    }

    public static Integer getSMSMessageType() {
        return 1;
    }

    public static Integer getEmailMessageType() {
        return 2;
    }

    public static Integer getQuartzMessageType() {
        return 3;
    }

    public static Integer getMessageUnSend() {
        return 0;
    }

    public static Integer getMessageUnRead() {
        return 1;
    }

    public static Integer getMessageReaded() {
        return 2;
    }

    public static Integer getMessageDeleted() {
        return 9;
    }
}
