package com.rjpa.controller.system;

import anno.Permission;
import com.google.gson.Gson;
import com.rjpa.AuthConfig.vo.User;
import com.rjpa.controller.IndexMvcController;
import com.rjpa.service.ISystemUserService;
import com.rjpa.vo.AdminUserV;
import com.rjpa.vo.IndexPageMenuV;
import model.Result;
import model.utils.SystemConstCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private static final ModelAndView userView = new ModelAndView("/system/userManager");
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
    @Permission(name = "系统用户列表", permissionName = "local.micoUSC.sys.sysUser", permissionUrl = "/sys/sysUser_")
    @RequestMapping(value = "/sysUser_{pageCurrent}_{pageSize}_{pageCount}", method = RequestMethod.GET)
    public ModelAndView sysUser_(HttpServletRequest request, @PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount) {
        // TODO 1 读取当前系统中所有的绑定用户
        Result r = iSystemUserService.getAdmins();
        AdminUserV adminv = new AdminUserV();
        userView.addObject(PAGE_USER_LIST_PO_NAME, r.getData());
        userView.addObject(PAGE_USER_PO_NAME, adminv);
        userView.addObject("errMsg", errMsg);
        // TODO 4 共通处理
        {
            SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                    .getAttribute("SPRING_SECURITY_CONTEXT");
            User u = (User) securityContextImpl.getAuthentication().getPrincipal();
            List<IndexPageMenuV> menuVS = u.getMenuVS();
            userView.addObject(IndexMvcController.MENU_REDIS_NAME, menuVS);
            userView.addObject("admin", u);
        }
        return userView;
    }

    @Permission(name = "系统用户管理初始化", permissionName = "local.micoUSC.sys.initSysUser", permissionUrl = "/sys/initSysUser")
    @RequestMapping(value = "/initSysUser", method = RequestMethod.GET)
    public ModelAndView initSysUser_(@RequestParam(value = "id") int id) {
        Result r = new Result();
        errMsg = new Result();
        // TODO 1 读取当前系统中所有的绑定用户
        AdminUserV adminv = iSystemUserService.getUserByUId(id);
        userView.addObject(PAGE_USER_PO_NAME, adminv);
        r = iSystemUserService.getAdmins();
        userView.addObject(PAGE_USER_LIST_PO_NAME, r.getData());
        userView.addObject("errMsg", errMsg);
        return userView;
    }

    /**
     * 添加系统用户
     */
    @Permission(name = "添加系统用户", permissionName = "local.micoUSC.sys.addSysUser", permissionUrl = "/sys/addSysUser")
    @RequestMapping(value = "/addSysUser", method = RequestMethod.POST)
    public ModelAndView addSysUser_(AdminUserV adminUserV) {
        errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 登陆名重复验证,绑定手机验证
        boolean checkFlg = iSystemUserService.checkUserExist(adminUserV.getUserName(), adminUserV.getPhoneNumber());
        // TODO 2 添加系统用户
        try {
            if (!checkFlg) {
                iSystemUserService.addAdmin(adminUserV);
            } else {
                errMsg = iSystemUserService.checkUserParamExist(adminUserV.getUserName(), adminUserV.getPhoneNumber());
            }
        } catch (Exception e) {
            errMsg = Result.error(SystemConstCode.ERROR.getMessage());
        }
        // TODO 2 读取当前系统中所有的绑定用户
        Result r = iSystemUserService.getAdmins();
        userView.addObject(PAGE_USER_LIST_PO_NAME, r.getData());
        userView.addObject("errMsg", errMsg);
        return userView;
    }

    /**
     * 修改系统用户
     */
    @Permission(name = "修改系统用户", permissionName = "local.micoUSC.sys.editSysUser", permissionUrl = "/sys/editSysUser")
    @RequestMapping(value = "/editSysUser", method = RequestMethod.POST)
    public ModelAndView editSysUser_(AdminUserV adminUserV) {
        errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminUserV checkData = iSystemUserService.checkUserExistByUId(adminUserV.getId());
        if (null == checkData || checkData.getId() == 0) {
            errMsg = Result.error(SystemConstCode.USER_NOT_FOUND.getCode(), SystemConstCode.USER_NOT_FOUND.getMessage());
        } else {
            try {
                // TODO 2 修改系统用户信息
                // TODO 2.2 比对页面用户信息是否修改的原用户信息
                AdminUserV userDB = iSystemUserService.getSysUserInfoByUserNameOrPhoneNumber(adminUserV.getUserName(), adminUserV.getPhoneNumber());
                if (userDB.getId() == checkData.getId()) {
                    adminUserV.setAddDate(userDB.getAddDate());
                    adminUserV.setState(userDB.getState());
                    iSystemUserService.editAdmin(adminUserV);
                } else {
                    errMsg = iSystemUserService.checkUserParamExist(adminUserV.getUserName(), adminUserV.getPhoneNumber());
                }
            } catch (Exception e) {
                errMsg = Result.error(SystemConstCode.ERROR.getMessage());
            }
        }
        // TODO 2 读取当前系统中所有的绑定用户
        Result r = iSystemUserService.getAdmins();
        userView.addObject(PAGE_USER_LIST_PO_NAME, r.getData());
        userView.addObject("errMsg", errMsg);
        return userView;
    }

    /**
     * 启用系统用户
     */
    @Permission(name = "启用系统用户", permissionName = "local.micoUSC.sys.actiSysUser", permissionUrl = "/sys/actiSysUser")
    @RequestMapping(value = "/actiSysUser", method = RequestMethod.POST)
    public void actiSysUser_(AdminUserV adminUserV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        errMsg = new Result();
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
    @Permission(name = "停用系统用户", permissionName = "local.micoUSC.sys.stopSysUser", permissionUrl = "/sys/stopSysUser")
    @RequestMapping(value = "/stopSysUser", method = RequestMethod.POST)
    public void stopSysUser_(AdminUserV adminUserV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        errMsg = new Result();
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
    @Permission(name = "删除系统用户", permissionName = "local.micoUSC.sys.delSysUser", permissionUrl = "/sys/delSysUser")
    @RequestMapping(value = "/delSysUser", method = RequestMethod.POST)
    public void delSysUser_(AdminUserV adminUserV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        errMsg = new Result();
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
}
