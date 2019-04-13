package com.rjpa.controller.system;

import anno.Permission;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.rjpa.controller.IndexMvcController;
import com.rjpa.repository.RedisDao;
import com.rjpa.service.ISystemUserService;
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
 * @ClassName: SystemRoleUserMvcController
 * @Description: 系统用户管理
 * @parm
 * @return
 * @author: drouis
 * @date: 2019/4/12 23:55
 */
@Controller
@RequestMapping(value = "/sys")
public class SystemRoleUserMvcController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String PAGE_USER_LIST_PO_NAME = "admins";
    public static final String PAGE_USER_PO_NAME = "adminv";
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
    @Permission(name = "系统用户列表", permissionName = "local.micoUSC.sys.sysUser")
    @RequestMapping(value = "/sysUser_{pageCurrent}_{pageSize}_{pageCount}", method = RequestMethod.GET)
    public ModelAndView sysUser_(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount) {
        // TODO 1 读取当前系统中所有的绑定用户
        Result r = iSystemUserService.getAdmins();
        AdminUserV adminv = new AdminUserV();
        indexView.addObject(PAGE_USER_LIST_PO_NAME, r.getData());
        indexView.addObject(PAGE_USER_PO_NAME, adminv);
        // TODO 2 共通数据设定
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

    @Permission(name = "系统用户管理初始化", permissionName = "local.micoUSC.sys.initSysUser")
    @RequestMapping(value = "/initSysUser", method = RequestMethod.GET)
    public ModelAndView initSysUser_(@RequestParam(value = "id") int id) {
        Result r = new Result();
        // TODO 1 读取当前系统中所有的绑定用户
        AdminUserV adminv = iSystemUserService.getUserByUId(id);
        indexView.addObject(PAGE_USER_PO_NAME, adminv);
        r = iSystemUserService.getAdmins();
        indexView.addObject(PAGE_USER_LIST_PO_NAME, r.getData());
        return indexView;
    }

    /**
     * 添加系统用户
     */
    @Permission(name = "添加系统用户", permissionName = "local.micoUSC.sys.addSysUser")
    @RequestMapping(value = "/addSysUser", method = RequestMethod.POST)
    public ModelAndView addSysUser_(AdminUserV adminUserV) {
        // TODO 1 用户信息验证
        // TODO 1.1 登陆名重复验证,绑定手机验证
        boolean checkFlg = iSystemUserService.checkUserExist(adminUserV.getUserName(), adminUserV.getPhoneNumber());
        // TODO 2 添加系统用户
        try {
            if (!checkFlg) {
                iSystemUserService.addAdmin(adminUserV);
            } else {
                errMsg = Result.error(SystemConstCode.USER_IS_EXIST.getCode(), SystemConstCode.USER_IS_EXIST.getMessage());
            }
        } catch (Exception e) {
            errMsg = Result.error(SystemConstCode.ERROR.getMessage());
        }
        // TODO 2 读取当前系统中所有的绑定用户
        Result r = iSystemUserService.getAdmins();
        indexView.addObject(PAGE_USER_LIST_PO_NAME, r.getData());
        indexView.addObject("errMsg", errMsg);
        return indexView;
    }

    /**
     * 修改系统用户
     */
    @Permission(name = "修改系统用户", permissionName = "local.micoUSC.sys.editSysUser")
    @RequestMapping(value = "/editSysUser", method = RequestMethod.POST)
    public ModelAndView editSysUser_(AdminUserV adminUserV) {
        Result errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminUserV checkData = iSystemUserService.checkUserExistByUId(adminUserV.getId());
        if (null == checkData || checkData.getId() == 0) {
            errMsg = Result.error(SystemConstCode.USER_NOT_FOUND.getCode(), SystemConstCode.USER_NOT_FOUND.getMessage());
        } else {
            // TODO 1.2 登陆名重复验证,绑定手机验证
            boolean checkFlg = iSystemUserService.checkUserExist(adminUserV.getUserName(), adminUserV.getPhoneNumber());
            try {
                // TODO 2 添加系统用户
                if (!checkFlg) {
                    iSystemUserService.addAdmin(adminUserV);
                } else {
                    errMsg = Result.error(SystemConstCode.USER_IS_EXIST.getCode(), SystemConstCode.USER_IS_EXIST.getMessage());
                }
            } catch (Exception e) {
                errMsg = Result.error(SystemConstCode.ERROR.getMessage());
            }
        }
        // TODO 2 读取当前系统中所有的绑定用户
        Result r = iSystemUserService.getAdmins();
        indexView.addObject(PAGE_USER_LIST_PO_NAME, r.getData());
        indexView.addObject("errMsg", errMsg);
        return indexView;
    }

    /**
     * 启用系统用户
     */
    @Permission(name = "启用系统用户", permissionName = "local.micoUSC.sys.actiSysUser")
    @RequestMapping(value = "/actiSysUser", method = RequestMethod.POST)
    public void actiSysUser_(AdminUserV adminUserV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Result errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminUserV checkData = iSystemUserService.checkUserExistByUId(adminUserV.getId());
        if (null == checkData || checkData.getId() == 0) {
            errMsg = Result.error(SystemConstCode.USER_NOT_FOUND.getCode(), SystemConstCode.USER_NOT_FOUND.getMessage());
        } else {
            try {
                // TODO 2 启用系统用户
                iSystemUserService.actiAdmin(checkData);
            } catch (Exception e) {
                errMsg = Result.error(SystemConstCode.ERROR.getMessage());
            }
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
    @Permission(name = "停用系统用户", permissionName = "local.micoUSC.sys.stopSysUser")
    @RequestMapping(value = "/stopSysUser", method = RequestMethod.POST)
    public void stopSysUser_(AdminUserV adminUserV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminUserV checkData = iSystemUserService.checkUserExistByUId(adminUserV.getId());
        if (null == checkData || checkData.getId() == 0) {
            errMsg = Result.error(SystemConstCode.USER_NOT_FOUND.getCode(), SystemConstCode.USER_NOT_FOUND.getMessage());
        } else {
            try {
                // TODO 2 停用系统用户
                iSystemUserService.forbAdmin(checkData);
            } catch (Exception e) {
                errMsg = Result.error(SystemConstCode.ERROR.getMessage());
            }
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
    @Permission(name = "删除系统用户", permissionName = "local.micoUSC.sys.delSysUser")
    @RequestMapping(value = "/delSysUser", method = RequestMethod.POST)
    public void delSysUser_(AdminUserV adminUserV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminUserV checkData = iSystemUserService.checkUserExistByUId(adminUserV.getId());
        if (null == checkData || checkData.getId() == 0) {
            errMsg = Result.error(SystemConstCode.USER_NOT_FOUND.getCode(), SystemConstCode.USER_NOT_FOUND.getMessage());
        } else {
            try {
                // TODO 2 停用系统用户
                iSystemUserService.delAdmin(checkData.getId());
            } catch (Exception e) {
                errMsg = Result.error(SystemConstCode.ERROR.getMessage());
            }
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
    private ISystemUserService iSystemUserService;
    @Autowired
    RedisDao redisDao;
}
