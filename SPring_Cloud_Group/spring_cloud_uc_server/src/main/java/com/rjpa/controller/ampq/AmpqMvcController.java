package com.rjpa.controller.ampq;

import anno.Permission;
import com.google.gson.Gson;
import com.rjpa.AuthConfig.vo.User;
import com.rjpa.controller.IndexMvcController;
import com.rjpa.service.IAmpqMessageService;
import com.rjpa.vo.IndexPageMenuV;
import feign.Param;
import model.Result;
import model.utils.SystemConstCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import vo.MessageAmpqV;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/ampq")
public class AmpqMvcController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    ModelAndView ampqView = new ModelAndView("/ampq/ampqManager");
    Result errMsg = new Result();
    Gson gson = new Gson();

    @Permission(name = "消息队列初始化界面", permissionName = "local.micoUSC.ampq.initPageView", permissionUrl = "/ampq/initSystemMQ")
    @RequestMapping(value = "/initSystemMQ", method = RequestMethod.GET)
    public ModelAndView initSystemMQ_(HttpServletRequest request, HttpServletResponse response) {
        // TODO 共通处理
        initCommonDatas(request);
        try {
            // TODO 1 界面内容初始化
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        ampqView.addObject("errMsg", errMsg);
        return ampqView;
    }

    @Permission(name = "更新系统消息状态", permissionName = "local.micoUSC.ampq.updateMessageStatus", permissionUrl = "/ampq/updateMessageStatus")
    @RequestMapping(value = "/updateMessageStatus_", method = RequestMethod.GET)
    public void updateMessageStatus_(@Param(value = "uuid") String uuid, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Result errMsg = new Result();
        try {
            // TODO 1 设置消息队列
            Result r = ampqMessageService.getMessageByUUID(uuid);
            if (null == r.getData()) {
                errMsg = Result.error(SystemConstCode.MESSAGE_HISTORYDATA_NOT_EXIST.getCode(), SystemConstCode.MESSAGE_HISTORYDATA_NOT_EXIST.getMessage());
            } else {
                MessageAmpqV v = (MessageAmpqV) r.getData();
                ampqMessageService.saveAmpqMessage(v, v.getMessageUnRead());
            }
            // TODO 2 返回更新数据结果 前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //TODO 用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            String result = callback + "(" + new Gson().toJson(errMsg) + ")";
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void initCommonDatas(HttpServletRequest request) {
        errMsg = new Result();
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                .getAttribute("SPRING_SECURITY_CONTEXT");
        User u = (User) securityContextImpl.getAuthentication().getPrincipal();
        List<IndexPageMenuV> menuVS = u.getMenuVS();
        ampqView.addObject(IndexMvcController.MENU_REDIS_NAME, menuVS);
        ampqView.addObject("admin", u);
        ampqView.addObject("errMsg", errMsg);
    }

    @Autowired
    IAmpqMessageService ampqMessageService;

}
