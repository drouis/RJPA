package com.rjpa.rabbitMQ;

import com.google.gson.Gson;
import com.rjpa.config.CustomRabbitConfiguration;
import com.rjpa.mic.client.AliyunSMSClient;
import com.rjpa.mic.repository.driverschool.Entity.AmpqMessageEntity;
import com.rjpa.mic.service.IAmpqService;
import com.rjpa.mic.service.ISmsMessageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vo.MessageAmpqV;
import vo.SmsMessageV;

import java.util.Date;

@Component
@RabbitListener(queues = CustomRabbitConfiguration.QUEUE_A)
public class CustomSMSRabbitMQReceiver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Gson gson = new Gson();

    @RabbitHandler
    public void process(String content) {
        logger.info("处理器Master接收处理队列" + CustomRabbitConfiguration.QUEUE_A + "当中的消息： " + content);
        // TODO 1 识别消息队列信息
        AmpqMessageEntity message = gson.fromJson(content, AmpqMessageEntity.class);
        // TODO 2 更新消息状态
        message.setAmpqStatue(MessageAmpqV.getMessageReaded());
        int exeCount = ampqService.updateMailAmpqMessage(message);
        if (exeCount > 0) {
            // TODO 3.1 调用阿里云发送短信，并写入待验证数据
            SmsMessageV smsMessage = gson.fromJson(message.getAmpqMemo(), SmsMessageV.class);
            if (smsMessage.getSmsType() == SmsMessageV.getLoginSMSType()) {
                SmsMessageV sendedSms = (SmsMessageV) smsClient.sendLoginVerifySMS(smsMessage,9).getData();
                if (!StringUtils.isEmpty(sendedSms.getSmsUuid())) {
                    smsMessage.setSmsContent(sendedSms.getSmsContent());
                    smsMessage.setSmsUuid(sendedSms.getSmsUuid());
                    smsMessage.setSendTime(new Date());
                }
            } else if (smsMessage.getSmsType() == SmsMessageV.getVerifyCodeSMSType()) {
                // TODO 3.2 调用阿里云普通验证短信模板，并发送短信
                SmsMessageV sendedSms = (SmsMessageV) smsClient.sendLoginVerifySMS(smsMessage,8).getData();
                if (!StringUtils.isEmpty(sendedSms.getSmsUuid())) {
                    smsMessage.setSmsContent(sendedSms.getSmsContent());
                    smsMessage.setSmsUuid(sendedSms.getSmsUuid());
                    smsMessage.setSendTime(new Date());
                }
            }else{
                // TODO 3.3 调用阿里云变更数据验证短信模板，并发送短信
                SmsMessageV sendedSms = (SmsMessageV) smsClient.sendLoginVerifySMS(smsMessage,0).getData();
                if (!StringUtils.isEmpty(sendedSms.getSmsUuid())) {
                    smsMessage.setSmsContent(sendedSms.getSmsContent());
                    smsMessage.setSmsUuid(sendedSms.getSmsUuid());
                    smsMessage.setSendTime(new Date());
                }
            }
            smsMessageService.saveSmsMessage(smsMessage);
        }
    }

    @Autowired
    IAmpqService ampqService;
    @Autowired
    AliyunSMSClient smsClient;
    @Autowired
    ISmsMessageService smsMessageService;
}
