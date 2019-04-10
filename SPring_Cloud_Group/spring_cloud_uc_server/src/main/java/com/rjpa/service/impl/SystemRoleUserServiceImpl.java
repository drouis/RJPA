package com.rjpa.service.impl;

import com.rjpa.repository.Entity.LzhAdminEntity;
import com.rjpa.repository.LzhAdminRepository;
import com.rjpa.service.ISystemRoleUserService;
import com.rjpa.vo.AdminUserV;
import model.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class SystemRoleUserServiceImpl implements ISystemRoleUserService {
    @Override
    public Result getAdmin() {
        return Result.ok(adminRepository.findAll());
    }

    @Override
    public AdminUserV getUserByUId(int id) {
        LzhAdminEntity adminDB = (LzhAdminEntity) adminRepository.getOne(id);
        AdminUserV po = BeanUtils.instantiateClass(AdminUserV.class);
        if (null != adminDB) {
            BeanUtils.copyProperties(adminDB, po);
        }
        return po;
    }

    @Override
    public Result addAdmin(AdminUserV adminUserV) {
        LzhAdminEntity admin = new LzhAdminEntity();
        BeanUtils.copyProperties(adminUserV, admin);
        adminRepository.save(admin);
        Result r = Result.ok(admin);
        return r;
    }

    @Override
    public boolean checkUserExist(String userName, String phoneNumber) {

        if (!StringUtils.isEmpty(userName)) {
            List checkList = (List) adminRepository.findByUserName(userName);
            if (null != checkList && 0 < checkList.size()) {
                return true;
            }
        }
        if (!StringUtils.isEmpty(phoneNumber)) {
            List checkList = (List) adminRepository.findByPhoneNumber(phoneNumber);
            if (null != checkList && 0 < checkList.size()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AdminUserV checkUserExistByUId(int id) {
        LzhAdminEntity adminDB = (LzhAdminEntity) adminRepository.getOne(id);
        AdminUserV po = BeanUtils.instantiateClass(AdminUserV.class);
        if (null != adminDB) {
            BeanUtils.copyProperties(adminDB, po);
        }
        return po;
    }

    @Override
    public Result editAdmin(AdminUserV adminUserV) {
        LzhAdminEntity admin = new LzhAdminEntity();
        BeanUtils.copyProperties(adminUserV, admin);
        adminRepository.save(admin);
        Result r = Result.ok(admin);
        return r;
    }

    @Override
    public Result forbAdmin(AdminUserV adminUserV) {
        LzhAdminEntity admin = new LzhAdminEntity();
        BeanUtils.copyProperties(adminUserV, admin);
        admin.setState(2);
        adminRepository.save(admin);
        Result r = Result.ok(admin);
        return r;
    }

    @Override
    public Result actiAdmin(AdminUserV adminUserV) {
        LzhAdminEntity admin = new LzhAdminEntity();
        BeanUtils.copyProperties(adminUserV, admin);
        admin.setState(1);
        adminRepository.save(admin);
        Result r = Result.ok(admin);
        return r;
    }

    @Override
    public Result delAdmin(int adminId) {
        LzhAdminEntity admin = (LzhAdminEntity) adminRepository.findById(adminId);
        admin.setState(3);
        adminRepository.save(admin);
        Result r = Result.ok(admin);
        return r;
    }

    @Autowired
    LzhAdminRepository adminRepository;
}
