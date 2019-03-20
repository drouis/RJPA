package com.rjpa.service;


import com.google.gson.Gson;
import com.rjpa.AuthConfig.vo.User;
import com.rjpa.feign.client.UserServiceFeignClient;
import model.Result;
import model.vo.LzhAdminEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Gson gs = new Gson();
        // TODO 1.0 数据库中查询用户信息
        Result r = userServiceFeignClient.getUserByUserName(s);
        LzhAdminEntity admin;
        User u = null;
        if (null != r && null != r.getData()) {
            admin = gs.fromJson(r.getData().toString(), LzhAdminEntity.class);
            BeanUtils.copyProperties(admin, u);
            // TODO 1.1 查询数据库中用户对应的访问权限
            Result r2 = userServiceFeignClient.getUserMenuRightsByUserId(admin.getId());
            List userMenuRights = gs.fromJson(r2.getData().toString(), ArrayList.class);
            u.setAllowPermissionService(userMenuRights);
        }
        return u;
    }
}
