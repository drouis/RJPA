package com.rjpa.controller.ampq;

import anno.Permission;
import com.google.gson.Gson;
import com.rjpa.config.RabbitMQConfiguration;
import com.rjpa.rabbitMq.CustomProducer;
import com.rjpa.service.IAmpqMessageService;
import com.rjpa.vo.MessageAmpqV;
import com.rjpa.vo.SmsMessageV;
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
public class AmpqSMSMvcController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    Gson gson = new Gson();

    // 发送短信消息队列请求
    @Permission(name = "发送短信消息队列请求", permissionName = "local.micoUSC.ampq.sendSMSMsg", permissionUrl = "/ampq/sendSMSMsg")
    @RequestMapping(value = "/sendSMSMsg", method = RequestMethod.POST)
    public void sendSMSMsg_(SmsMessageV smsMessage, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Result errMsg = new Result();
        try {
            // TODO 1 设置消息队列
            MessageAmpqV v = new MessageAmpqV();
            v.setAmpqQueName(RabbitMQConfiguration.EXCHANGE_SMS);
            v.setAmpqStatue(v.getMessageUnSend());
            v.setAmpqMemo(gson.toJson(v));
            v.setAmpqType(v.getSMSMessageType());
            v.setAmpqClass(SmsMessageV.class.getName());
            v = ampqMessageService.addAmpqMessage(v);
            // TODO 2 发送消息队列
            customProducer.sendSMSMsg(gson.toJson(v));
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
