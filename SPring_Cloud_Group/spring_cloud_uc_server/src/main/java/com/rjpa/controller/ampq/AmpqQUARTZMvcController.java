package com.rjpa.controller.ampq;

import anno.Permission;
import com.google.gson.Gson;
import com.rjpa.rabbitMq.CustomProducer;
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
            customProducer.sendQUARTZMsg(content);
            // TODO 2 返回更新数据结果 前端传过来的回调函数名称
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
}