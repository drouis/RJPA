package com.rjpa.controller;

import com.google.gson.Gson;
import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;
import com.rjpa.service.IndexMvcService;
import com.rjpa.vo.IndexPageMenuV;
import model.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexMvcController {

    @RequestMapping(value = {"/welcome", "/"}, method = RequestMethod.GET)
    public String welcome() {
        Gson gson = new Gson();
        Result r = new Result();
        // TODO 权限管理 MenusPermissionMvcController
        // 获取全部主菜单，二级菜单，adminMenusRights
        List<IndexPageMenuV> menuVS = new ArrayList<IndexPageMenuV>();
        r = indexMvcService.getAdminMenusRights(-1);
        List<Map> mainMenus = (List) r.getData();
        for (Map pMenuMap : mainMenus) {
            IndexPageMenuV pageMenus = new IndexPageMenuV();
            LzhAdminMenusRightsEntity xngData = gson.fromJson(gson.toJson(pMenuMap), LzhAdminMenusRightsEntity.class);
            BeanUtils.copyProperties(xngData, pageMenus);
            r = indexMvcService.getAdminMenusRights(xngData.getId());
            List<Map> subMenus = (List) r.getData();
            List<LzhAdminMenusRightsEntity> subList = new ArrayList<LzhAdminMenusRightsEntity>();
            for (Map sMenuMap : subMenus) {
                LzhAdminMenusRightsEntity xngSubData = gson.fromJson(gson.toJson(sMenuMap), LzhAdminMenusRightsEntity.class);
                subList.add(xngSubData);
            }
            pageMenus.setSubMenus(subList);
            menuVS.add(pageMenus);
        }
        // 获取全部系统操作权限名称链接，adminPermission
        // TODO 角色管理 RolePermissionMvcController
        // 获取全部权限，adminRole
        // 获取全部用户角色绑定的权限，adminRolePermission

        // TODO 用户管理 UserRoleMvcController
        // 获取全部绑定权限的用户，adminUserRole
        // 获取平台全部用户，admin


        return "index";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @Autowired
    IndexMvcService indexMvcService;

}
