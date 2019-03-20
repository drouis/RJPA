package com.rjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"authorizationRequest"})
public class UserMvcController {
    /**
     * 用户登陆
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "usname") String usname) {
        return new ModelAndView("login");
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
}