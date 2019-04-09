package com.rjpa.service.impl;

import com.rjpa.repository.LzhAdminRepository;
import com.rjpa.service.ILoginService;
import model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {

    @Override
    public Result getUserByUserName(String userName) {
        return null;
    }

    @Override
    public Result getUserByPhone(String phone) {
        return null;
    }

    @Override
    public Result getUserMenuRightsByUserId(Integer id) {
        return null;
    }

    @Override
    public Result getRoleByUserId(Integer id) {
        return null;
    }

    @Override
    public Result getAdminPermissionUrls() {
        return null;
    }

    @Autowired
    LzhAdminRepository adminRepository;
}
