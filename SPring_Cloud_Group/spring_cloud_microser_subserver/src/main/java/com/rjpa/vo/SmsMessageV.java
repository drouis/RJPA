package com.rjpa.vo;

import java.io.Serializable;

public class SmsMessageV extends MessageAmpqV implements Serializable {
    private static final long serialVersionUID = 1981634345281805048L;
    String inPhoneNumber;
    String outPhoneNumber;
    String smsContent;
    Long sendTIme;

    public String getInPhoneNumber() {
        return inPhoneNumber;
    }

    public void setInPhoneNumber(String inPhoneNumber) {
        this.inPhoneNumber = inPhoneNumber;
    }

    public String getOutPhoneNumber() {
        return outPhoneNumber;
    }

    public void setOutPhoneNumber(String outPhoneNumber) {
        this.outPhoneNumber = outPhoneNumber;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public Long getSendTIme() {
        return sendTIme;
    }

    public void setSendTIme(Long sendTIme) {
        this.sendTIme = sendTIme;
    }
}
