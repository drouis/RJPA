package com.rjpa.vo;

import java.io.Serializable;

public class MailMessageV extends MessageAmpqV implements Serializable {

    private static final long serialVersionUID = -4874878727807730612L;
    String mailFrom;
    String mailTo;
    String mailCc;
    String mailSubject;
    String mailPageModel;
    String mailContent;

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailCc() {
        return mailCc;
    }

    public void setMailCc(String mailCc) {
        this.mailCc = mailCc;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailPageModel() {
        return mailPageModel;
    }

    public void setMailPageModel(String mailPageModel) {
        this.mailPageModel = mailPageModel;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
}
