package vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

@ApiModel(value = "短信信息")
public class SmsMessageV extends MessageAmpqV implements Serializable {
    private static final long serialVersionUID = 4969819125555778764L;
    @ApiModelProperty(value = "数据主键")
    String smsUuid;
    @ApiModelProperty(value = "消息手机号")
    String inPhoneNumber;
    @ApiModelProperty(value = "短信内容")
    String smsContent;
    @ApiModelProperty(value = "发送时间")
    Date sendTime;
    @ApiModelProperty(value = "消息类型")
    Integer smsType;
    @ApiModelProperty(value = "消息状态")
    Integer smsStatus;
    String smsStatusStr;
    @ApiModelProperty(value = "短信验证状态区分")
    Integer verifyFlg;

    public String getSmsUuid() {
        return smsUuid;
    }

    public void setSmsUuid(String smsUuid) {
        this.smsUuid = smsUuid;
    }

    public String getInPhoneNumber() {
        return inPhoneNumber;
    }

    public void setInPhoneNumber(String inPhoneNumber) {
        this.inPhoneNumber = inPhoneNumber;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public Integer getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(Integer smsStatus) {
        this.smsStatus = smsStatus;
    }

    public String getSmsStatusStr() {
        return smsStatusStr;
    }

    public void setSmsStatusStr(String smsStatusStr) {
        this.smsStatusStr = smsStatusStr;
    }

    public Integer getVerifyFlg() {
        return verifyFlg;
    }

    public void setVerifyFlg(Integer verifyFlg) {
        this.verifyFlg = verifyFlg;
    }

    public static Integer getVerifyCodeSMSType() {
        return 8;
    }

    public static Integer getLoginSMSType() {
        return 9;
    }

    public static Integer getReadSMS() {
        return 5;
    }

    public static Integer getUnReadSMS() {
        return 1;
    }

    public static Integer getLoginUnVerifySMS() {
        return 0;
    }

    public static Integer getLoginVerifySMS() {
        return 1;
    }

    public final static String getSMSVerifyCodeWithSizeCount(int size) {
        StringBuilder vierifyCodeBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            vierifyCodeBuilder.append(random.nextInt(10));
        }
        return vierifyCodeBuilder.toString();
    }

    public static Integer getMessageSended() {
        return 9;
    }

    public static final String ALIYUN_ACCESSKEY = "LTAIbL04ZW5lDz5L";
    public static final String ALIYUN_ACCESSKEY_SECRET = "V97dNXJ9tVKqErIKL15DWJcBY7iIoL";
    public static final String ALIYUN_SMSMESSAGE_SIGN = "";
    public static String SMS_MESSGE_DENGLUMIMA_SAMPLE[] = {"SMS_165361237", "尊敬的用户，您的注册会员动态密码为：${code}，请勿泄漏于他人！"};
    public static String SMS_MESSGE_SHENFENYANZHENG_SAMPLE[] = {"SMS_165336351", "验证码${code}，您正在进行身份验证，打死不要告诉别人哦！"};
    public static String SMS_MESSGE_DENGLUQUEREN_SAMPLE[] = {"SMS_165336350", "验证码${code}，您正在登录，若非本人操作，请勿泄露。"};
    public static String SMS_MESSGE_DENGLUYICHANG_SAMPLE[] = {"SMS_165336349", "验证码${code}，您正尝试异地登录，若非本人操作，请勿泄露。"};
    public static String SMS_MESSGE_YONGHUZHUCE_SAMPLE[] = {"SMS_165336348", "验证码${code}，您正在注册成为新用户，感谢您的支持！"};
    public static String SMS_MESSGE_XIUGAIMIMA_SAMPLE[] = {"SMS_165336347", "验证码${code}，您正在尝试修改登录密码，请妥善保管账户信息。"};
    public static String SMS_MESSGE_XINXIBIANGENG_SAMPLE[] = {"SMS_165336346", "验证码${code}，您正在尝试变更重要信息，请妥善保管账户信息。"};

}
