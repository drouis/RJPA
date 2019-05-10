package com.rjpa.vo;

import com.rjpa.mic.repository.driverschool.Entity.AmpqMessageEntity;

import java.io.Serializable;

public class MessageAmpqV extends AmpqMessageEntity implements Serializable {
    private static final long serialVersionUID = 3890206202648764677L;

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
