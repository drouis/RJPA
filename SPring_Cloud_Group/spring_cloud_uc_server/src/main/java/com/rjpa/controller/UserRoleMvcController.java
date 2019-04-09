package com.rjpa.controller;

import com.rjpa.feign.client.UserServiceFeignClient;
import com.rjpa.service.CommonLogsService;
import model.Result;
import model.utils.SystemConstCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户管理
 */
@Controller
@SessionAttributes({"authorizationRequest"})
public class UserRoleMvcController {
    private static final String loginDataPrexStr = "shiro_login_count_";

    ModelAndView loginV = new ModelAndView("login");

    /**
     * 用户登陆
     */
    @RequestMapping(value = "/Authlogin",method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "usname") String usname,
                              @RequestParam(value = "phoneNumber") String phoneNumber,
                              @RequestParam(value = "password") String password) {
        Result r = new Result();
        String loginDataName = null;
        if (!StringUtils.isEmpty(usname)) {
            r = userServiceFeignClient.getUserByUserName(usname);
            loginDataName = loginDataPrexStr + usname;
        } else {
            r = userServiceFeignClient.getUserByPhone(phoneNumber);
            loginDataName = loginDataPrexStr + phoneNumber;
        }
        if (null != r && Integer.parseInt(r.getCode()) == (SystemConstCode.SUCCESS.getCode())) {
            return new ModelAndView("redirect:/welcome");
        }
        r = Result.error(SystemConstCode.USER_NOT_FOUND.getCode(), SystemConstCode.USER_NOT_FOUND.getMessage());
        loginV.addObject("errMsg", r);
        // 记录用户登陆日志 adminLoginLog
        commonLogsService.insertOperLogByUser(loginDataName);
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