package com.rjpa.repository.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "lzh_admin_sms", schema = "system", catalog = "")
public class LzhAdminSmsEntity {
    private int id;
    private String smsUuid;
    private String inPhoneNumber;
    private String smsContent;
    private Timestamp sendTime;
    private Integer smsType;
    private Integer smsStatus;
    private String smsStatusStr;
    private Integer verifyFlg;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sms_uuid", nullable = true, length = 32)
    public String getSmsUuid() {
        return smsUuid;
    }

    public void setSmsUuid(String smsUuid) {
        this.smsUuid = smsUuid;
    }

    @Basic
    @Column(name = "in_phone_number", nullable = true, length = 15)
    public String getInPhoneNumber() {
        return inPhoneNumber;
    }

    public void setInPhoneNumber(String inPhoneNumber) {
        this.inPhoneNumber = inPhoneNumber;
    }

    @Basic
    @Column(name = "sms_content", nullable = true, length = 400)
    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    @Basic
    @Column(name = "send_time", nullable = true)
    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    @Basic
    @Column(name = "sms_type", nullable = true)
    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    @Basic
    @Column(name = "sms_status", nullable = true)
    public Integer getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(Integer smsStatus) {
        this.smsStatus = smsStatus;
    }

    @Basic
    @Column(name = "sms_status_str", nullable = true, length = 200)
    public String getSmsStatusStr() {
        return smsStatusStr;
    }

    public void setSmsStatusStr(String smsStatusStr) {
        this.smsStatusStr = smsStatusStr;
    }

    @Basic
    @Column(name = "verify_flg", nullable = true)
    public Integer getVerifyFlg() {
        return verifyFlg;
    }

    public void setVerifyFlg(Integer verifyFlg) {
        this.verifyFlg = verifyFlg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LzhAdminSmsEntity that = (LzhAdminSmsEntity) o;
        return id == that.id &&
                Objects.equals(smsUuid, that.smsUuid) &&
                Objects.equals(inPhoneNumber, that.inPhoneNumber) &&
                Objects.equals(smsContent, that.smsContent) &&
                Objects.equals(sendTime, that.sendTime) &&
                Objects.equals(smsType, that.smsType) &&
                Objects.equals(smsStatus, that.smsStatus) &&
                Objects.equals(smsStatusStr, that.smsStatusStr) &&
                Objects.equals(verifyFlg, that.verifyFlg);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, smsUuid, inPhoneNumber, smsContent, sendTime, smsType, smsStatus, smsStatusStr, verifyFlg);
    }
}
