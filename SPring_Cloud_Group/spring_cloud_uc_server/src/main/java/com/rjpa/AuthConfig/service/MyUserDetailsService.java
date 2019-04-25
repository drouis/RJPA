package com.rjpa.AuthConfig.service;

import com.rjpa.AuthConfig.vo.User;
import com.rjpa.repository.Entity.LzhAdminEntity;
import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;
import com.rjpa.service.ILoginService;
import com.rjpa.service.IndexMvcService;
import com.rjpa.vo.AdminPermissionV;
import com.rjpa.vo.AdminRoleV;
import com.rjpa.vo.IndexPageMenuV;
import model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class MyUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LzhAdminEntity admin = (LzhAdminEntity) loginService.getUserByUserName(s).getData();
        if (admin == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 自定义错误的文章说明的地址：http://blog.csdn.net/z69183787/article/details/21190639?locationNum=1&fps=1
        if (admin.getState() == 2 || admin.getState() == 3) {
            throw new LockedException("用户账号被冻结，无法登陆请联系管理员！");
        }
        logger.info("用户的用户名: {}", admin.getRealName()); // TODO 根据用户名，查找到对应的密码，与权限
        String password = passwordEncoder().encode(admin.getPassword());
        logger.info("password: {}", password);
        // TODO 判定用户是否存在管理员角色，管理员绑定全部权限，否则绑定用户权限数据
        List<AdminRoleV> rList = (List<AdminRoleV>) loginService.getRoleByUserId(admin.getId()).getData();
        boolean adminFlg = false;
        for (AdminRoleV r : rList) {
            if (r.getRole().equals("admin")) {
                adminFlg = true;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        List<AdminPermissionV> permissions = new ArrayList<AdminPermissionV>();
        // TODO 绑定管理员角色的全部系统权限
        if (adminFlg) {
            permissions = (List<AdminPermissionV>) loginService.getAdminPermissionUrls().getData();
        } else {
            // TODO 绑定系统用户角色的系统权限
            permissions = (List<AdminPermissionV>) loginService.getUserMenuRightsByUserId(admin.getId()).getData();
        }
        List<IndexPageMenuV> menuVS = initUserPermissionMenus(admin.getId(), adminFlg);
        for (int i = 0; i < permissions.size(); ) {
            if (i < permissions.size() - 1) {
                sb.append(permissions.get(i).getPermission() + ",");
            } else {
                sb.append(permissions.get(i).getPermission());
            }
            i++;
        }
        // TODO 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
        User user = new User();
        BeanUtils.copyProperties(admin, user);
        user.initUser(admin.getUserName(), password, AuthorityUtils.commaSeparatedStringToAuthorityList(sb.toString()), menuVS, adminFlg);
        return user;
    }

    private List<IndexPageMenuV> initUserPermissionMenus(int uid, boolean adminFlg) {
        Result r = new Result();
        //TODO 获取全部主菜单，二级菜单，adminMenusRights
        List<IndexPageMenuV> menuVS = new ArrayList<IndexPageMenuV>();
        try {
            if (adminFlg) {
                r = indexMvcService.getAdminMenusRights(-1);
            } else {
                r = indexMvcService.getUserMenusRights(-1, uid);
            }
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
        return menuVS;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    ILoginService loginService;
    @Autowired
    IndexMvcService indexMvcService;
}
