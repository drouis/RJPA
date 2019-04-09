package com.rjpa.service.impl;

import com.rjpa.repository.Entity.LzhAdminEntity;
import com.rjpa.repository.LzhAdminRepository;
import com.rjpa.service.ILoginService;
import model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登陆界面业务service
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Override
    public Result getUserByUserName(String userName) {
        Result r = new Result();
        List<LzhAdminEntity> ls = adminRepository.findByUserName(userName);
        if (null != ls && 0 < ls.size()) {
            return new Result().ok(ls.get(0));
        }
        return r;
    }

    @Override
    public Result getUserByPhone(String phone) {
        Result r = new Result();
        List<LzhAdminEntity> ls = adminRepository.findByPhoneNumber(phone);
        if (null != ls && 0 < ls.size()) {
            return new Result().ok(ls.get(0));
        }
        return r;
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
