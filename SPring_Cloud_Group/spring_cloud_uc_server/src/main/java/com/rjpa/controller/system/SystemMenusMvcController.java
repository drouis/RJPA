package com.rjpa.controller.system;

import anno.Permission;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.rjpa.controller.IndexMvcController;
import com.rjpa.redis.RedisDao;
import com.rjpa.service.ISystemMenuService;
import com.rjpa.service.IndexMvcService;
import com.rjpa.vo.AdminMenuV;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SystemMenusMvcController
 * @Description: 菜单管理
 * @parm
 * @return
 * @author: drouis
 * @date: 2019/4/12 23:56
 */
@Controller
@RequestMapping("/sys")
public class SystemMenusMvcController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String PAGE_MENU_LIST_PO_NAME = "adminMenus";
    public static final String PAGE_MENU_PO_NAME = "adminMenuv";
    private static final ModelAndView menuView = new ModelAndView("/system/menuManager");

    Result errMsg = new Result();
    Gson gson = new Gson();


    /**
     * @return
     * @ClassName:
     * @Description: 系统菜单列表
     * @parm
     * @author: drouis
     * @date: 2019/4/12 23:59
     */
    @Permission(name = "系统菜单列表",permissionName = "local.micoUSC.sys.sysMenu")
    @RequestMapping(value = "/sysMenu_{pageCurrent}_{pageSize}_{pageCount}", method = RequestMethod.GET)
    public ModelAndView sysUser_(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount) {
        // TODO 1 读取当前系统中所有的菜单
        Result r = iSystemMenuService.getSubMenusByMId(-1);
        AdminMenuV menuv = new AdminMenuV();
        menuView.addObject(PAGE_MENU_LIST_PO_NAME, r.getData());
        menuView.addObject(PAGE_MENU_PO_NAME, menuv);

        // TODO 2 取得全部菜单列表作为下拉菜单
        List<SelectObject> pageSelectV = new ArrayList<SelectObject>();
        List<AdminMenuV> allMenus = (List) iSystemMenuService.getMenus().getData();
        for (AdminMenuV allMenu : allMenus) {
            SelectObject selectObject = new SelectObject();
            selectObject.setSelectText(allMenu.getName());
            selectObject.setSelectValue(String.valueOf(allMenu.getId()));
            if (allMenu.getId() == menuv.getParentid()) {
                selectObject.setSelected("selected");
            }
            pageSelectV.add(selectObject);
        }
        menuView.addObject("bundMenuSelect", pageSelectV);
        // TODO 3 共通数据设定
        List<IndexPageMenuV> menuVS = new ArrayList<IndexPageMenuV>();
        try {
            String str = redisDao.getCacheObject(IndexMvcController.MENU_REDIS_NAME);
            JSONArray ts = JSON.parseArray(str);
            for (int i = 0; i < ts.size(); i++) {
                IndexPageMenuV menu = gson.fromJson(ts.get(i).toString(), IndexPageMenuV.class);
                menuVS.add(menu);
            }
            menuView.addObject(IndexMvcController.MENU_REDIS_NAME, menuVS);
        } catch (Exception e) {

        }
        menuView.addObject("errMsg", errMsg);
        return menuView;
    }

    @Permission(name = "系统菜单初始化",permissionName = "local.micoUSC.sys.initSysMenu")
    @RequestMapping(value = "/initSysMenu", method = RequestMethod.GET)
    public ModelAndView initSysRole_(@RequestParam(value = "id") int mid) {
        // TODO 1 读取当前系统中所有的绑定菜单
        AdminMenuV adminMenuV = iSystemMenuService.getMenuByMId(mid);
        menuView.addObject(PAGE_MENU_PO_NAME, adminMenuV);
        Result r = iSystemMenuService.getMenus();
        menuView.addObject(PAGE_MENU_LIST_PO_NAME, r.getData());
        // TODO 3 取得全部菜单列表作为下拉菜单
        List<SelectObject> pageSelectV = new ArrayList<SelectObject>();
        List<AdminMenuV> allMenus = (List) iSystemMenuService.getMenus().getData();
        for (AdminMenuV allMenu : allMenus) {
            SelectObject selectObject = new SelectObject();
            selectObject.setSelectText(allMenu.getName());
            selectObject.setSelectValue(String.valueOf(allMenu.getId()));
            if (allMenu.getId() == adminMenuV.getParentid()) {
                selectObject.setSelected("selected");
            }
            pageSelectV.add(selectObject);
        }

        menuView.addObject("errMsg", errMsg);
        menuView.addObject("bundMenuSelect", pageSelectV);
        return menuView;
    }

    /**
     * 添加系统菜单
     */
    @Permission(name = "添加系统菜单",permissionName = "local.micoUSC.sys.addSysMenu")
    @RequestMapping(value = "/addSysMenu", method = RequestMethod.POST)
    public ModelAndView addSysRole_(AdminMenuV adminMenuV) {
        // TODO 1 菜单信息验证
        if (adminMenuV.getId() == 0 && adminMenuV.getBundMenuSelect() == -1) {
            // TODO 1.1 登陆名重复验证,绑定手机验证
            boolean checkFlg = iSystemMenuService.checkMenuExist(adminMenuV.getName(), adminMenuV.getPermission());
            try {
                // TODO 2 添加系统菜单
                if (!checkFlg) {
                    adminMenuV.setParentid(-1);
                    iSystemMenuService.addMenu(adminMenuV);
                } else {
                    errMsg = Result.error(SystemConstCode.MENU_IS_EXIST.getCode(), SystemConstCode.MENU_IS_EXIST.getMessage());
                }
            } catch (Exception e) {
                errMsg = Result.error(SystemConstCode.ERROR.getMessage());
            }
            return new ModelAndView("redirect:/sys/sysMenu_0_0_0");
        } else {
            // TODO 1.1 验证子菜单是否存在
            boolean checkFlg = iSystemMenuService.checkMenuExist(adminMenuV.getName(), adminMenuV.getPermission(), adminMenuV.getBundMenuSelect());
            try {
                // TODO 2 添加系统子菜单
                if (!checkFlg) {
                    adminMenuV.setParentid(adminMenuV.getBundMenuSelect());
                    iSystemMenuService.addMenu(adminMenuV);
                } else {
                    errMsg = Result.error(SystemConstCode.MENU_IS_EXIST.getCode(), SystemConstCode.MENU_IS_EXIST.getMessage());
                }
            } catch (Exception e) {
                errMsg = Result.error(SystemConstCode.ERROR.getMessage());
            }

            // TODO 2 读取当前系统中所有的绑定菜单
            Result r = iSystemMenuService.getSubMenusByMId(adminMenuV.getParentid());
            menuView.addObject(PAGE_MENU_LIST_PO_NAME, r.getData());
        }
        menuView.addObject("errMsg", errMsg);
        return menuView;
    }

    /**
     * 修改系统菜单
     */
    @Permission(name = "修改系统菜单",permissionName = "local.micoUSC.sys.editSysMenu")
    @RequestMapping(value = "/editSysMenu", method = RequestMethod.POST)
    public ModelAndView editSysRole_(AdminMenuV adminMenuV) {
        Result errMsg = new Result();
        // TODO 1 菜单信息验证
        // TODO 1.1 系统菜单存在验证
        AdminMenuV checkData = iSystemMenuService.checkMenuExistByMId(adminMenuV.getId());
        if (null == checkData || checkData.getId() == 0) {
            errMsg = Result.error(SystemConstCode.MENU_NOT_FOUND.getCode(), SystemConstCode.MENU_NOT_FOUND.getMessage());
        } else {
            // TODO 1.2 菜单名称重复验证,菜单标示验证
            boolean checkFlg = iSystemMenuService.checkMenuExist(adminMenuV.getName(), adminMenuV.getPermission());
            try {
                // TODO 2 添加系统菜单
                if (!checkFlg) {
                    iSystemMenuService.addMenu(adminMenuV);
                }
            } catch (Exception e) {
                errMsg = Result.error(SystemConstCode.ERROR.getMessage());
            }
        }

        // TODO 2 读取当前系统中所有的绑定菜单
        Result r = iSystemMenuService.getMenus();
        menuView.addObject(PAGE_MENU_LIST_PO_NAME, r.getData());
        menuView.addObject("errMsg", errMsg);
        return menuView;
    }

    /**
     * 删除系统菜单
     */
    @Permission(name = "删除系统菜单",permissionName = "local.micoUSC.sys.delSysMenu")
    @RequestMapping(value = "/delSysMenu", method = RequestMethod.POST)
    public void delSysRole_(AdminMenuV adminMenuV, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = new Result();
        // TODO 1 菜单信息验证
        // TODO 1.1 系统菜单存在验证
        AdminMenuV checkData = iSystemMenuService.checkMenuExistByMId(adminMenuV.getId());
        if (null == checkData || checkData.getId() == 0) {
            errMsg = Result.error(SystemConstCode.MENU_NOT_FOUND.getCode(), SystemConstCode.MENU_NOT_FOUND.getMessage());
        } else {
            try {
                // TODO 2 停用系统菜单
                iSystemMenuService.delMenu(adminMenuV.getId());
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
     * 添加系统子菜单
     */
    @Permission(name = "添加系统子菜单",permissionName = "local.micoUSC.sys.getBundSubMenu")
    @RequestMapping(value = "/getBundSubMenu", method = RequestMethod.GET)
    public ModelAndView getBundSubMenu_(AdminMenuV adminMenuV) {
        // TODO 1 菜单信息验证
        // TODO 1.1 取得父菜单数据
        AdminMenuV parentMenuObj = iSystemMenuService.checkMenuExistByMId(adminMenuV.getId());
        menuView.addObject(PAGE_MENU_PO_NAME, new AdminMenuV());
        // TODO 2 读取当前菜单中绑定的子菜单
        Result r = iSystemMenuService.getSubMenusByMId(parentMenuObj.getId());
        menuView.addObject(PAGE_MENU_LIST_PO_NAME, r.getData());
        // TODO 3 取得全部菜单列表作为下拉菜单
        List<SelectObject> pageSelectV = new ArrayList<SelectObject>();
        List<AdminMenuV> allMenus = (List) iSystemMenuService.getMenus().getData();
        for (AdminMenuV allMenu : allMenus) {
            SelectObject selectObject = new SelectObject();
            selectObject.setSelectText(allMenu.getName());
            selectObject.setSelectValue(String.valueOf(allMenu.getId()));
            if (allMenu.getId() == parentMenuObj.getId()) {
                selectObject.setSelected("selected");
            }
            pageSelectV.add(selectObject);
        }
        menuView.addObject("bundMenuSelect", pageSelectV);

        menuView.addObject("errMsg", errMsg);
        return menuView;
    }

    @Autowired
    ISystemMenuService iSystemMenuService;
    @Autowired
    IndexMvcService indexMvcService;
    @Autowired
    RedisDao redisDao;
}
