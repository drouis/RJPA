package com.rjpa.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.rjpa.controller.IndexMvcController;
import com.rjpa.repository.Entity.LzhAdminEntity;
import com.rjpa.repository.Entity.LzhAdminPermissionEntity;
import com.rjpa.repository.RedisDao;
import com.rjpa.service.ISystemPermissionService;
import com.rjpa.service.ISystemRoleService;
import com.rjpa.service.ISystemRoleUserService;
import com.rjpa.vo.AdminPermissionV;
import com.rjpa.vo.AdminRoleV;
import com.rjpa.vo.AdminUserV;
import com.rjpa.vo.IndexPageMenuV;
import model.Result;
import model.SelectObject;
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
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 角色管理
 */
@Controller
@RequestMapping(value = "/sys")
public class SystemRolePermissionMvcController {
    public static final String PAGE_ROLE_LIST_PO_NAME = "adminRoles";
    public static final String PAGE_ROLE_PO_NAME = "adminRolev";
    public static final String PAGE_BUND_ROLE_USERS_NAME = "bundRoleUsers";
    public static final String PAGE_BUND_ROLE_PERMISSIONS_NAME = "bundRolePermissions";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final ModelAndView roleView = new ModelAndView("/system/roleManager");
    Result errMsg = new Result();
    Gson gson = new Gson();

    /**
     * 系统角色列表
     *
     * @param pageCurrent
     * @param pageSize
     * @param pageCount
     * @return
     */
    @RequestMapping(value = "/sysRole_{pageCurrent}_{pageSize}_{pageCount}", method = RequestMethod.GET)
    public ModelAndView sysRole_(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount) {
        // TODO 1 读取当前系统中所有的角色
        Result r = iSystemRoleService.getRoles();
        AdminRoleV adminRoleV = new AdminRoleV();
        roleView.addObject(PAGE_ROLE_LIST_PO_NAME, r.getData());
        roleView.addObject(PAGE_ROLE_PO_NAME, adminRoleV);
        String str = redisDao.getCacheObject(IndexMvcController.MENU_REDIS_NAME);
        JSONArray ts = JSON.parseArray(str);
        List<IndexPageMenuV> menuVS = new ArrayList<IndexPageMenuV>();
        for (int i = 0; i < ts.size(); i++) {
            IndexPageMenuV menu = gson.fromJson(ts.get(i).toString(), IndexPageMenuV.class);
            menuVS.add(menu);
        }
        roleView.addObject(IndexMvcController.MENU_REDIS_NAME, menuVS);
        roleView.addObject(PAGE_BUND_ROLE_USERS_NAME, new ArrayList<SelectObject>());
        return roleView;
    }

    @RequestMapping(value = "/initSysRole", method = RequestMethod.GET)
    public ModelAndView initSysRole_(@RequestParam(value = "id") int id) {
        Result r = new Result();
        // TODO 1 读取当前系统中所有的绑定用户
        AdminRoleV adminRoleV = iSystemRoleService.getRoleByRId(id);
        roleView.addObject(PAGE_ROLE_PO_NAME, adminRoleV);
        r = iSystemRoleService.getRoles();
        roleView.addObject(PAGE_ROLE_LIST_PO_NAME, r.getData());
        roleView.addObject(PAGE_BUND_ROLE_USERS_NAME, new ArrayList<SelectObject>());
        roleView.addObject(PAGE_BUND_ROLE_PERMISSIONS_NAME, new ArrayList<SelectObject>());
        return roleView;
    }

    /**
     * 添加系统用户
     */
    @RequestMapping(value = "/addSysRole", method = RequestMethod.POST)
    public ModelAndView addSysRole_(AdminRoleV adminRoleV) {
        // 1 用户信息验证
        // 1.1 登陆名重复验证,绑定手机验证
        boolean checkFlg = iSystemRoleService.checkRoleExist(adminRoleV.getName(), adminRoleV.getRole());
        // 2 添加系统用户
        if (!checkFlg) {
            iSystemRoleService.addRole(adminRoleV);
        }
        try {
            // 2 读取当前系统中所有的绑定用户
            Result r = iSystemRoleService.getRoles();
            roleView.addObject(PAGE_ROLE_LIST_PO_NAME, r.getData());
        } catch (Exception e) {
            errMsg = Result.error(SystemConstCode.ERROR.getMessage());
        }
        roleView.addObject("errMsg", errMsg);
        return roleView;
    }

    /**
     * 修改系统用户
     */
    @RequestMapping(value = "/editSysRole", method = RequestMethod.POST)
    public ModelAndView editSysRole_(AdminRoleV adminRoleV) {
        Result errMsg = new Result();
        // 1 用户信息验证
        // 1.1 系统用户存在验证
        AdminRoleV checkData = iSystemRoleService.checkRoleExistByUId(new Long(adminRoleV.getId()).intValue());
        if (checkData.getId() == 0) {

        }
        // 1.2 登陆名重复验证,绑定手机验证
        boolean checkFlg = iSystemRoleService.checkRoleExist(adminRoleV.getName(), adminRoleV.getRole());
        try {
            // 2 添加系统用户
            if (!checkFlg) {
                iSystemRoleService.addRole(adminRoleV);
            }
        } catch (Exception e) {
            errMsg = Result.error(SystemConstCode.ERROR.getMessage());
        }
        // 2 读取当前系统中所有的绑定用户
        Result r = iSystemRoleService.getRoles();
        roleView.addObject(PAGE_ROLE_LIST_PO_NAME, r.getData());
        roleView.addObject("errMsg", errMsg);
        return roleView;
    }

    /**
     * 启用系统用户
     */
    @RequestMapping(value = "/actiSysRole", method = RequestMethod.POST)
    public void actiSysRole_(AdminRoleV AdminRoleV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminRoleV checkData = iSystemRoleService.checkRoleExistByUId(new Long(AdminRoleV.getId()).intValue());
        if (checkData.getId() == 0) {

        }
        try {
            // TODO 2 启用系统用户
            iSystemRoleService.actiRole(checkData);
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
    @RequestMapping(value = "/stopSysRole", method = RequestMethod.POST)
    public void stopSysRole_(AdminRoleV adminRoleV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminRoleV checkData = iSystemRoleService.checkRoleExistByUId(new Long(adminRoleV.getId()).intValue());
        if (checkData.getId() == 0) {

        }
        try {
            // TODO 2 停用系统用户
            iSystemRoleService.forbRole(checkData);
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
    @RequestMapping(value = "/delSysRole", method = RequestMethod.POST)
    public void delSysRole_(AdminRoleV adminRoleV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = new Result();
        // TODO 1 用户信息验证
        // TODO 1.1 系统用户存在验证
        AdminRoleV checkData = iSystemRoleService.checkRoleExistByUId(new Long(adminRoleV.getId()).intValue());
        if (checkData.getId() == 0) {

        }
        try {
            // TODO 2 停用系统用户
            iSystemRoleService.delRole(new Long(checkData.getId()).intValue());
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
     * 获取角色下的全部用户
     */
    @RequestMapping(value = "/getBundRoleUser", method = RequestMethod.GET)
    public ModelAndView getBundRoleUser_(AdminRoleV adminRoleV) {
        Result errMsg = new Result();
        // TODO 1. 取得角色下的用户列表，对比系统绑定的数据，返回页面数据
        {
            // TODO 1.1 取得角色下的用户列表，全部系统用户列表
            Result r = iSystemRoleUserService.getAdmins();
            List<LzhAdminEntity> allUsers = (List) r.getData();
            // TODO 1.2 循环系统用户列表，角色下用户map进行对比，设定选定值
            List<AdminUserV> bundUsers = iSystemRoleUserService.getBundRoleUsers(adminRoleV.getId());
            Iterator itu = bundUsers.iterator();
            Map bundUserMap = new HashMap();
            while (itu.hasNext()) {
                AdminUserV iu = (AdminUserV) itu.next();
                bundUserMap.put(iu.getId(), iu.getUserName());
            }
            // TODO 1.3 返回比对数据结果
            List<SelectObject> pageSelectV = new ArrayList<SelectObject>();
            for (int i = 0; i < allUsers.size(); i++) {
                SelectObject selectObject = new SelectObject();
                selectObject.setSelectText(allUsers.get(i).getRealName());
                selectObject.setSelectValue(String.valueOf(allUsers.get(i).getId()));
                String checkStr = (String) bundUserMap.get(allUsers.get(i).getId());
                if (!StringUtils.isEmpty(checkStr)) {
                    selectObject.setSelected("selected");
                }
                pageSelectV.add(selectObject);
            }
            roleView.addObject(PAGE_BUND_ROLE_USERS_NAME, pageSelectV);
        }
        // TODO 2. 取得角色下的权限列表，对比系统绑定的数据，返回页面数据
        {
            // TODO 2.1 取得角色下的权限列表，全部系统权限列表
            Result r = iSystemPermissionService.getPermissions();
            List<LzhAdminPermissionEntity> allPermissions = (List) r.getData();
            // TODO 2.2 循环系统权限列表，角色下权限map进行对比，设定选定值
            List<AdminPermissionV> bundPermissions = iSystemPermissionService.getBundRolePermissions(adminRoleV.getId());
            Iterator itu = bundPermissions.iterator();
            Map bundPermissionMap = new HashMap();
            while (itu.hasNext()) {
                AdminPermissionV iu = (AdminPermissionV) itu.next();
                bundPermissionMap.put(iu.getId(), iu.getName());
            }
            // TODO 2.3 返回比对数据结果
            List<SelectObject> pageSelectV = new ArrayList<SelectObject>();
            for (int i = 0; i < allPermissions.size(); i++) {
                SelectObject selectObject = new SelectObject();
                selectObject.setSelectText(allPermissions.get(i).getName());
                selectObject.setSelectValue(String.valueOf(allPermissions.get(i).getId()));
                String checkStr = (String) bundPermissionMap.get(allPermissions.get(i).getId());
                if (!StringUtils.isEmpty(checkStr)) {
                    selectObject.setSelected("selected");
                }
                pageSelectV.add(selectObject);
            }
            roleView.addObject(PAGE_BUND_ROLE_PERMISSIONS_NAME, pageSelectV);
        }
        // TODO 6 共通数据设定
        Result r = iSystemRoleService.getRoles();
        roleView.addObject(PAGE_ROLE_LIST_PO_NAME, r.getData());
        roleView.addObject(PAGE_ROLE_PO_NAME, adminRoleV);
        String str = redisDao.getCacheObject(IndexMvcController.MENU_REDIS_NAME);
        JSONArray ts = JSON.parseArray(str);
        List<IndexPageMenuV> menuVS = new ArrayList<IndexPageMenuV>();
        for (int i = 0; i < ts.size(); i++) {
            IndexPageMenuV menu = gson.fromJson(ts.get(i).toString(), IndexPageMenuV.class);
            menuVS.add(menu);
        }
        roleView.addObject(IndexMvcController.MENU_REDIS_NAME, menuVS);
        return roleView;
    }

    /**
     * 绑定角色下的选定用户
     */
    @RequestMapping(value = "/bundRoleUser", method = RequestMethod.POST)
    public ModelAndView bundRoleUser_(AdminRoleV adminRoleV,
                                      @RequestParam(value = "bundUserIds", defaultValue = "") String bundUserIds,
                                      HttpServletRequest request, HttpServletResponse response) {
        String bundUserId[] = bundUserIds.split(",");
        iSystemRoleUserService.bundRoleUsers(bundUserId, adminRoleV.getId());
        return new ModelAndView("redirect:/sys/getBundRoleUser?id=" + adminRoleV.getId());
    }

    /**
     * 绑定角色下的选定权限
     */
    @RequestMapping(value = "/bundRolePermission", method = RequestMethod.POST)
    public ModelAndView bundRolePermission_(AdminRoleV adminRoleV,
                                            @RequestParam(value = "bundRolePermissionIds", defaultValue = "") String bundRolePermissionIds,
                                            HttpServletRequest request, HttpServletResponse response) {
        String bundPermissionId[] = bundRolePermissionIds.split(",");
        iSystemPermissionService.bundRolePermissions(bundPermissionId, adminRoleV.getId());
        return new ModelAndView("redirect:/sys/getBundRoleUser?id=" + adminRoleV.getId());
    }

    @Autowired
    private ISystemPermissionService iSystemPermissionService;
    @Autowired
    private ISystemRoleUserService iSystemRoleUserService;
    @Autowired
    private ISystemRoleService iSystemRoleService;
    @Autowired
    RedisDao redisDao;
}
