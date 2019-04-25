package com.rjpa.vo;

import com.rjpa.repository.Entity.AmpqMessageEntity;

import java.io.Serializable;

public class MessageAmpqV extends AmpqMessageEntity implements Serializable {
    private static final long serialVersionUID = 3890206202648764677L;

    public Integer getSMSMessageType() {
        return 1;
    }

    public Integer getEmailMessageType() {
        return 2;
    }

    public Integer getQuartzMessageType() {
        return 3;
    }

    public Integer getMessageUnSend() {
        return 0;
    }

    public Integer getMessageUnRead() {
        return 1;
    }

    public Integer getMessageReaded() {
        return 2;
    }

    public Integer getMessageDeleted() {
        return 9;
    }
}
