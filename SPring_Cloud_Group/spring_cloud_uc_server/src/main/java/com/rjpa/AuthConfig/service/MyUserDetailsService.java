package com.rjpa.AuthConfig.service;

import com.rjpa.repository.Entity.LzhAdminEntity;
import com.rjpa.service.ILoginService;
import com.rjpa.vo.AdminPermissionV;
import com.rjpa.vo.AdminRoleV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
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
        for (int i = 0; i < permissions.size(); ) {
            if (i < permissions.size() - 1) {
                sb.append(permissions.get(i).getPermission() + ",");
            } else {
                sb.append(permissions.get(i).getPermission());
            }
            i++;
        }
        // TODO 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
        User user = new User(admin.getUserName(), password, AuthorityUtils.commaSeparatedStringToAuthorityList(sb.toString()));
        return user;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    ILoginService loginService;
}
