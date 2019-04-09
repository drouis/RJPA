package com.rjpa.controller;

import com.google.gson.Gson;
import com.rjpa.feign.client.UserServiceFeignClient;
import com.rjpa.service.CommonLogsService;
import model.Result;
import model.utils.SystemConstCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 用户管理
 */
@Controller
@SessionAttributes({"authorizationRequest"})
public class UserRoleMvcController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String loginDataPrexStr = "shiro_login_count_";

    ModelAndView loginV = new ModelAndView("/login");

    /**
     * 用户登陆
     */
    @RequestMapping(value = "/Authlogin",method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "usname") String usname,
                              @RequestParam(value = "phoneNumber") String phoneNumber,
                              @RequestParam(value = "password") String password) {
        Result r = new Result();
        String loginDataName = null;
        try {
            if (!StringUtils.isEmpty(usname)) {
                loginDataName = loginDataPrexStr + usname;
                r = userServiceFeignClient.getUserByUserName(usname);
            } else {
                loginDataName = loginDataPrexStr + phoneNumber;
                r = userServiceFeignClient.getUserByPhone(phoneNumber);
            }
            if (null != r && Integer.parseInt(r.getCode()) == (SystemConstCode.SUCCESS.getCode())) {
                return new ModelAndView(new RedirectView("/micoUSC/u/Welcome"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage().toString());
        }
        r = Result.error(SystemConstCode.USER_NOT_FOUND.getCode(), SystemConstCode.USER_NOT_FOUND.getMessage());
        // 记录用户登陆日志 adminLoginLog
        commonLogsService.insertOperLogByUser(loginDataName);
        loginV.addObject("phoneNumber",phoneNumber);
        loginV.addObject("usname","FUCKKKKKKKKKKKKKKKKKK");
        loginV.addObject("errMsg", new Gson().toJson(r));
        return loginV;
    }

    /**
     * 用户登出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(@RequestParam(value = "usname") String usname) {
        return new ModelAndView("login");
    }

    /**
     * 用户授权
     */
    @RequestMapping(value = "/author", method = RequestMethod.GET)
    public ModelAndView author(@RequestParam(value = "usname") String usname) {
        return new ModelAndView("login");
    }

    @Autowired
    UserServiceFeignClient userServiceFeignClient;
    @Autowired
    CommonLogsService commonLogsService;
}