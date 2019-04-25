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
public class AmpqEMAILMvcController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 发送邮件消息队列页面初始化

    // 编辑发送邮件消息队列页面

    // 发送邮件消息队列请求
    @Permission(name = "发送邮件消息队列请求", permissionName = "local.micoUSC.ampq.sendEMAILMsg", permissionUrl = "/ampq/sendEMAILMsg")
    @RequestMapping(value = "/sendEMAILMsg", method = RequestMethod.GET)
    public void sendEMAILMsg_(@Param(value = "content") String content, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = new Result();
        try {
            // TODO 1 设置消息队列
            customProducer.sendEMAILMsg(content);
            MessageAmpqV v = new MessageAmpqV();
            v.setAmpqQueName(RabbitMQConfiguration.EXCHANGE_EMAIL);
            v.setAmpqStatue(v.getMessageUnSend());
            v.setAmpqMemo(content);
            v.setAmpqType(v.getEmailMessageType());
            v.setAmpqClass(AmpqEMAILMvcController.MmailMessage.class.getName());
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

    class MmailMessage extends MessageAmpqV {
        String mailFrom;
        String mailTo;
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

    @Autowired
    CustomProducer customProducer;
    @Autowired
    IAmpqMessageService ampqMessageService;
}
