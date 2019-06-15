package com.rjpa.mic.client;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import model.Result;
import model.utils.SystemConstCode;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import vo.SmsMessageV;

import java.util.HashMap;
import java.util.Map;

@Service
public class AliyunSMSClient {
    static Gson gson = new Gson();

    public Result sendLoginVerifySMS(SmsMessageV messageV, Integer smsSampleFlg) {
        Result r = new Result();
        DefaultProfile profile = DefaultProfile.getProfile("default", SmsMessageV.ALIYUN_ACCESSKEY, SmsMessageV.ALIYUN_ACCESSKEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        // 支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。
        request.putQueryParameter("PhoneNumbers", messageV.getInPhoneNumber());
        // 短信签名名称。请在控制台签名管理页面签名名称一列查看。
        request.putQueryParameter("SignName", SmsMessageV.ALIYUN_SMSMESSAGE_SIGN);
        // 短信模板ID。请在控制台模板管理页面模板CODE一列查看
        switch (smsSampleFlg) {
            case 9:
                request.putQueryParameter("TemplateCode", SmsMessageV.SMS_MESSGE_DENGLUMIMA_SAMPLE[0]);
                break;
            case 8:
                request.putQueryParameter("TemplateCode", SmsMessageV.SMS_MESSGE_SHENFENYANZHENG_SAMPLE[0]);
                break;
            default:
                request.putQueryParameter("TemplateCode", SmsMessageV.SMS_MESSGE_XINXIBIANGENG_SAMPLE[0]);
                break;
        }
        // 短信模板变量对应的实际值，JSON格式。
        Map tmpParam = new HashMap();
        tmpParam.put("code", messageV.getSmsContent());
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(tmpParam));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            Map m = gson.fromJson(response.getData(), HashMap.class);
            if (m.get("Code").toString().equals("OK")) {
                messageV.setSmsStatus(200);
                messageV.setSmsContent(SmsMessageV.SMS_MESSGE_DENGLUMIMA_SAMPLE[1].replace("${code}", messageV.getSmsContent()));
            } else {
                messageV.setSmsStatus(SystemConstCode.END_STR.getCode());
                messageV.setSmsStatusStr(m.get("Code").toString());
            }
            r = Result.ok(messageV);
        } catch (ServerException e) {
            messageV.setSmsStatus(SystemConstCode.END_STR.getCode());
            messageV.setSmsStatusStr(e.getMessage());
            r = Result.ok(messageV);
        } catch (ClientException e) {
            messageV.setSmsStatus(SystemConstCode.END_STR.getCode());
            messageV.setSmsStatusStr(e.getMessage());
            r = Result.ok(messageV);
        }
        return r;
    }
}
