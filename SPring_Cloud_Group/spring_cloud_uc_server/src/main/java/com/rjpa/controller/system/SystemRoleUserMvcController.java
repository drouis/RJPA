package com.rjpa.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.rjpa.controller.IndexMvcController;
import com.rjpa.repository.RedisDao;
import com.rjpa.service.ISystemRoleUserService;
import com.rjpa.vo.AdminUserV;
import com.rjpa.vo.IndexPageMenuV;
import model.Result;
import model.utils.SystemConstCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户管理
 */
@Controller
@RequestMapping(value = "/sys")
public class SystemRoleUserMvcController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final ModelAndView indexView = new ModelAndView("/system/userManager");
    Result errMsg = new Result();
    Gson gson = new Gson();

    /**
     * 系统用户列表
     *
     * @param pageCurrent
     * @param pageSize
     * @param pageCount
     * @return
     */
    @RequestMapping(value = "/sysUser_{pageCurrent}_{pageSize}_{pageCount}", method = RequestMethod.GET)
    public ModelAndView sysUser_(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount) {
        // TODO 1 读取当前系统中所有的绑定用户
        Result r = iSystemRoleUserService.getAdmins();
        AdminUserV adminv = new AdminUserV();
        indexView.addObject("admins", r.getData());
        indexView.addObject("adminv", adminv);
        String str = redisDao.getCacheObject(IndexMvcController.MENU_REDIS_NAME);
        JSONArray ts = JSON.parseArray(str);
        List<IndexPageMenuV> menuVS = new ArrayList<IndexPageMenuV>();
        for (int i = 0; i < ts.size(); i++) {
            IndexPageMenuV menu = gson.fromJson(ts.get(i).toString(), IndexPageMenuV.class);
            menuVS.add(menu);
        }
        indexView.addObject(IndexMvcController.MENU_REDIS_NAME, menuVS);
        return indexView;
    }

    @RequestMapping(value = "/initSysUser", method = RequestMethod.GET)
    public ModelAndView initSysUser_(@RequestParam(value = "id") int id) {
        Result r = new Result();
        // TODO 1 读取当前系统中所有的绑定用户
        AdminUserV adminv = iSystemRoleUserService.getUserByUId(id);
        indexView.addObject("adminv", adminv);
        r = iSystemRoleUserService.getAdmins();
        indexView.addObject("admins", r.getData());
        return indexView;
    }

    /**
     * 添加系统用户
     */
    @RequestMapping(value = "/addSysUser", method = RequestMethod.POST)
    public ModelAndView addSysUser_(AdminUserV adminUserV) {
        // TODO 1 用户信息验证
        // TODO 1.1 登陆名重复验证,绑定手机验证
        boolean checkFlg = iSystemRoleUserService.checkUserExist(adminUserV.getUserName(), adminUserV.getPhoneNumber());
        // TODO 2 添加系统用户
        if (!checkFlg) {
            iSystemRoleUserService.addAdmin(adminUserV);
        }
        try {
            // TODO 2 读取当前系统中所有的绑定用户
            Result r = iSystemRoleUserService.getAdmins();
            indexView.addObject("admins", r.getData());
        } catch (Exception e) {
            errMsg = Result.error(SystemConstCode.ERROR.getMessage());
        }

        indexView.addObject("errMsg", errMsg);
        return indexView;
    }

    /**
     * 修改系统用户
     */
    @RequestMapping(value = "/editSysUser", method = RequestMethod.POST)
    public ModelAndView editSysUser_(AdminUserV adminUserV) {
        Result errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminUserV checkData = iSystemRoleUserService.checkUserExistByUId(adminUserV.getId());
        if (checkData.getId() == 0) {

        }
        // TODO 1.2 登陆名重复验证,绑定手机验证
        boolean checkFlg = iSystemRoleUserService.checkUserExist(adminUserV.getUserName(), adminUserV.getPhoneNumber());
        try {
            // TODO 2 添加系统用户
            if (!checkFlg) {
                iSystemRoleUserService.addAdmin(adminUserV);
            }
        } catch (Exception e) {
            errMsg = Result.error(SystemConstCode.ERROR.getMessage());
        }
        // TODO 2 读取当前系统中所有的绑定用户
        Result r = iSystemRoleUserService.getAdmins();
        indexView.addObject("admins", r.getData());
        indexView.addObject("errMsg", errMsg);
        return indexView;
    }

    /**
     * 启用系统用户
     */
    @RequestMapping(value = "/actiSysUser", method = RequestMethod.POST)
    public void actiSysUser_(AdminUserV adminUserV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Result errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminUserV checkData = iSystemRoleUserService.checkUserExistByUId(adminUserV.getId());
        if (checkData.getId() == 0) {

        }
        try {
            // TODO 2 启用系统用户
            iSystemRoleUserService.actiAdmin(checkData);
        } catch (Exception e) {
            errMsg = Result.error(SystemConstCode.ERROR.getMessage());
        }
        // TODO 3 返回更新数据结果
        try {
            //前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            String result = callback + "(" + new Gson().toJson(errMsg) + ")";
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 停用系统用户
     */
    @RequestMapping(value = "/stopSysUser", method = RequestMethod.POST)
    public void stopSysUser_(AdminUserV adminUserV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminUserV checkData = iSystemRoleUserService.checkUserExistByUId(adminUserV.getId());
        if (checkData.getId() == 0) {

        }
        try {
            // TODO 2 停用系统用户
            iSystemRoleUserService.forbAdmin(checkData);
        } catch (Exception e) {
            errMsg = Result.error(SystemConstCode.ERROR.getMessage());
        }
        // TODO 3 返回更新数据结果
        try {
            //前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            String result = callback + "(" + new Gson().toJson(errMsg) + ")";
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 删除系统用户
     */
    @RequestMapping(value = "/delSysUser", method = RequestMethod.POST)
    public void delSysUser_(AdminUserV adminUserV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminUserV checkData = iSystemRoleUserService.checkUserExistByUId(adminUserV.getId());
        if (checkData.getId() == 0) {

        }
        try {
            // TODO 2 停用系统用户
            iSystemRoleUserService.delAdmin(checkData.getId());
        } catch (Exception e) {
            errMsg = Result.error(SystemConstCode.ERROR.getMessage());
        }
        // TODO 3 返回更新数据结果
        try {
            //前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            String result = callback + "(" + new Gson().toJson(errMsg) + ")";
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Autowired
    private ISystemRoleUserService iSystemRoleUserService;
    @Autowired
    RedisDao redisDao;
}
