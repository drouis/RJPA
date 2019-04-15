package com.rjpa.controller;

import anno.Permission;
import com.google.gson.Gson;
import com.rjpa.redis.RedisDao;
import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;
import com.rjpa.service.IndexMvcService;
import com.rjpa.vo.IndexPageMenuV;
import model.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexMvcController {
    public static final String MENU_REDIS_NAME = "menuVS";
    private static final ModelAndView indexView = new ModelAndView("index");
    Gson gson = new Gson();

    @Permission(name = "系统平台首页", permissionName = "local.micoUSC.welcome", permissionUrl = "/welcome")
    @RequestMapping(value = {"/welcome", "/"}, method = RequestMethod.GET)
    public ModelAndView welcome() {
        // tokenstr
        // redis -> tokenstr
        Result r = new Result();
        // TODO 1 权限管理 MenusPermissionMvcController
        // 获取全部主菜单，二级菜单，adminMenusRights
        List<IndexPageMenuV> menuVS = new ArrayList<IndexPageMenuV>();// TODO REDIS 中取得菜单数据
        try {
            r = indexMvcService.getAdminMenusRights(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<LzhAdminMenusRightsEntity> mainMenus = (List) r.getData();
        int i = 0;
        for (LzhAdminMenusRightsEntity pMenuMap : mainMenus) {
            IndexPageMenuV pageMenus = new IndexPageMenuV();
            BeanUtils.copyProperties(pMenuMap, pageMenus);
            if (i == 0) {
                pageMenus.setSelected("selected");
            }
            r = indexMvcService.getAdminMenusRights(pMenuMap.getId());
            List<LzhAdminMenusRightsEntity> subMenus = (List) r.getData();
            List<LzhAdminMenusRightsEntity> subList = new ArrayList<LzhAdminMenusRightsEntity>();
            for (LzhAdminMenusRightsEntity sMenuMap : subMenus) {
                subList.add(sMenuMap);
            }
            pageMenus.setSubMenus(subList);
            menuVS.add(pageMenus);
            i++;
        }
        // 获取全部系统操作权限名称链接，adminPermission
        // TODO 2 角色管理 RolePermissionMvcController
        // 获取全部权限，adminRole
        // 获取全部用户角色绑定的权限，adminRolePermission

        // TODO 3 用户管理 UserRoleMvcController
        // 获取全部绑定权限的用户，adminUserRole
        // 获取平台全部用户，admin

        indexView.addObject(MENU_REDIS_NAME, menuVS);
        redisDao.setCacheObject(MENU_REDIS_NAME, gson.toJson(menuVS));
        return indexView;
    }

    @RequestMapping(value = {"/Login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @Autowired
    IndexMvcService indexMvcService;
    @Autowired
    RedisDao redisDao;
}
