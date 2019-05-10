package com.rjpa.controller.ampq;

import anno.Permission;
import com.google.gson.Gson;
import com.rjpa.config.RabbitMQConfiguration;
import com.rjpa.rabbitMq.CustomProducer;
import com.rjpa.service.IAmpqMessageService;
import com.rjpa.vo.MailMessageV;
import com.rjpa.vo.MessageAmpqV;
import model.Result;
import model.utils.SystemConstCode;
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
    Gson gson = new Gson();

    // 发送邮件消息队列请求
    @Permission(name = "发送邮件消息队列请求", permissionName = "local.micoUSC.ampq.sendEMAILMsg", permissionUrl = "/ampq/sendEMAILMsg")
    @RequestMapping(value = "/sendEMAILMsg", method = RequestMethod.POST)
    public void sendEMAILMsg_(MailMessageV mailMessage, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = new Result();
        try {
            // TODO 1 设置消息队列
            MessageAmpqV v = mailMessage;
            v.setAmpqQueName(RabbitMQConfiguration.EXCHANGE_EMAIL);
            v.setAmpqStatue(v.getMessageUnSend());
            v.setAmpqType(v.getEmailMessageType());
            v.setAmpqMemo(gson.toJson(v));
            v.setAmpqClass(MailMessageV.class.getName());
            v = ampqMessageService.addAmpqMessage(v);
            // TODO 2 发送消息队列
            customProducer.sendEMAILMsg(gson.toJson(v));
            errMsg = Result.ok(v);
        } catch (Exception e) {
            errMsg = Result.error(SystemConstCode.ERROR.getMessage());
            logger.error(e.getMessage());
        }
        // TODO 3 返回更新数据结果
        try {
            // TODO 返回更新数据结果 前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //TODO 用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            String result = callback + "(" + new Gson().toJson(errMsg) + ")";
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Autowired
    CustomProducer customProducer;
    @Autowired
    IAmpqMessageService ampqMessageService;
}
