package com.rjpa.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统用户管理
 */
@Controller
@RequestMapping(value = "/sys")
public class SystemRoleUserMvcController {
    private static final ModelAndView indexView = new ModelAndView("/system/userManager");

    /**
     * 系统用户列表
     *
     * @param pageCurrent
     * @param pageSize
     * @param pageCount
     * @return
     */
    @RequestMapping(value = "/sysUser_{pageCurrent}_{pageSize}_{pageCount}", method = RequestMethod.GET)
    public ModelAndView initSysUser_(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount) {
        // 1 读取当前系统中所有的绑定用户

        return indexView;
    }

    /**
     * 添加系统用户
     */

    /**
     * 修改系统用户
     */

    /**
     * 启用系统用户
     */

    /**
     * 停用系统用户
     */

}
