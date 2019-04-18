package com.rjpa.controller;

import anno.Permission;
import com.rjpa.AuthConfig.vo.User;
import com.rjpa.redis.RedisDao;
import com.rjpa.service.IndexMvcService;
import com.rjpa.vo.IndexPageMenuV;
import model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexMvcController {
    public static final String MENU_REDIS_NAME = "menuVS";
    private static final ModelAndView indexView = new ModelAndView("index");
    Result errMsg = new Result();

    @Permission(name = "系统平台首页", permissionName = "local.micoUSC.welcome", permissionUrl = "/welcome")
    @RequestMapping(value = {"/welcome", "/"}, method = RequestMethod.GET)
    public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) {
        // TODO 1 权限管理 MenusPermissionMvcController
        // 获取全部系统操作权限名称链接，adminPermission
        // TODO 2 角色管理 RolePermissionMvcController
        // 获取全部权限，adminRole
        // 获取全部用户角色绑定的权限，adminRolePermission
        // TODO 3 用户管理 UserRoleMvcController
        // 获取全部绑定权限的用户，adminUserRole
        // 获取平台全部用户，admin

        // TODO 4 共通处理
        {
            SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                    .getAttribute("SPRING_SECURITY_CONTEXT");
            User u = (User) securityContextImpl.getAuthentication().getPrincipal();
            List<IndexPageMenuV> menuVS = u.getMenuVS();
            indexView.addObject(IndexMvcController.MENU_REDIS_NAME, menuVS);
            indexView.addObject("admin", u);
        }
        return indexView;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView login = new ModelAndView();
        login.addObject("errMsg", errMsg);
        return login;
    }

    @Autowired
    IndexMvcService indexMvcService;
    @Autowired
    RedisDao redisDao;
}
