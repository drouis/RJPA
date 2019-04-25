package com.rjpa.controller.ampq;

import anno.Permission;
import com.google.gson.Gson;
import com.rjpa.config.RabbitMQConfiguration;
import com.rjpa.rabbitMq.CustomProducer;
import com.rjpa.service.IAmpqMessageService;
import com.rjpa.vo.MessageAmpqV;
import feign.Param;
import model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/ampq")
public class AmpqSMSMvcController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 发送短信消息队列页面初始化

    // 编辑发送短信消息队列页面

    // 发送短信消息队列请求
    @Permission(name = "发送短信消息队列请求", permissionName = "local.micoUSC.ampq.sendSMSMsg", permissionUrl = "/ampq/sendSMSMsg")
    @RequestMapping(value = "/sendSMSMsg", method = RequestMethod.GET)
    public void sendSMSMsg_(@Param(value = "content") String content, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Result errMsg = new Result();
        try {
            // TODO 1 设置消息队列
            customProducer.sendEMAILMsg(content);
            MessageAmpqV v = new MessageAmpqV();
            v.setAmpqQueName(RabbitMQConfiguration.EXCHANGE_SMS);
            v.setAmpqStatue(v.getMessageUnSend());
            v.setAmpqMemo(content);
            v.setAmpqType(v.getEmailMessageType());
            v.setAmpqClass(AmpqSMSMvcController.SmsMessage.class.getName());
            ampqMessageService.addAmpqMessage(v);
            // TODO 2 返回更新数据结果 前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //TODO 用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            String result = callback + "(" + new Gson().toJson(errMsg) + ")";
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    class SmsMessage extends MessageAmpqV {
        String phoneNumber;
        String smsContent;
        Long sendTIme;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
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

    @Autowired
    CustomProducer customProducer;
    @Autowired
    IAmpqMessageService ampqMessageService;
}
