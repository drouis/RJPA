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
public class AmpqQUARTZMvcController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 发送定时任务消息队列页面初始化

    // 编辑发送定时任务消息队列页面

    // 发送定时任务消息队列请求
    @Permission(name = "发送定时任务消息队列请求", permissionName = "local.micoUSC.ampq.sendQUARTZMsg", permissionUrl = "/ampq/sendQUARTZMsg")
    @RequestMapping(value = "/sendQUARTZMsg", method = RequestMethod.GET)
    public void sendQUARTZMsg_(@Param(value = "content") String content, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Result errMsg = new Result();
        try {
            // TODO 1 设置消息队列
            customProducer.sendEMAILMsg(content);
            MessageAmpqV v = new MessageAmpqV();
            v.setAmpqQueName(RabbitMQConfiguration.EXCHANGE_QUARTZ);
            v.setAmpqStatue(v.getMessageUnSend());
            v.setAmpqMemo(content);
            v.setAmpqType(v.getEmailMessageType());
            v.setAmpqClass(AmpqQUARTZMvcController.QuartzMessage.class.getName());
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

    class QuartzMessage extends MessageAmpqV {
        String cronName;
        String cronTriger;
        String cronClass;
        String cronTag;
        String cronExp;
        int cronScheduFlg;
        Long qutzTime;

        public String getCronName() {
            return cronName;
        }

        public void setCronName(String cronName) {
            this.cronName = cronName;
        }

        public String getCronTriger() {
            return cronTriger;
        }

        public void setCronTriger(String cronTriger) {
            this.cronTriger = cronTriger;
        }

        public String getCronClass() {
            return cronClass;
        }

        public void setCronClass(String cronClass) {
            this.cronClass = cronClass;
        }

        public String getCronTag() {
            return cronTag;
        }

        public void setCronTag(String cronTag) {
            this.cronTag = cronTag;
        }

        public String getCronExp() {
            return cronExp;
        }

        public void setCronExp(String cronExp) {
            this.cronExp = cronExp;
        }

        public int getCronScheduFlg() {
            return cronScheduFlg;
        }

        public void setCronScheduFlg(int cronScheduFlg) {
            this.cronScheduFlg = cronScheduFlg;
        }

        public Long getQutzTime() {
            return qutzTime;
        }

        public void setQutzTime(Long qutzTime) {
            this.qutzTime = qutzTime;
        }
    }

    @Autowired
    CustomProducer customProducer;
    @Autowired
    IAmpqMessageService ampqMessageService;
}
