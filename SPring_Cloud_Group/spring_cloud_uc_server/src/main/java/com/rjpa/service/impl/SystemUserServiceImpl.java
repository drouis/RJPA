package com.rjpa.service.impl;

import com.rjpa.repository.Entity.LzhAdminEntity;
import com.rjpa.repository.Entity.LzhAdminUserRoleEntity;
import com.rjpa.repository.LzhAdminRepository;
import com.rjpa.repository.LzhAdminUserRoleRepository;
import com.rjpa.service.ISystemUserService;
import com.rjpa.vo.AdminUserV;
import model.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemUserServiceImpl implements ISystemUserService {
    @Override
    public Result getAdmins() {
        return Result.ok(adminRepository.findAll());
    }

    @Override
    public AdminUserV getUserByUId(int uid) {
        LzhAdminEntity adminDB = (LzhAdminEntity) adminRepository.getOne(uid);
        AdminUserV po = BeanUtils.instantiateClass(AdminUserV.class);
        if (null != adminDB) {
            BeanUtils.copyProperties(adminDB, po);
        }
        return po;
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
    public AdminUserV checkUserExistByUId(int uid) {
        LzhAdminEntity adminDB = (LzhAdminEntity) adminRepository.getOne(uid);
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
        admin.setState(1);
        admin.setAddDate(new Date(System.currentTimeMillis()));
        adminRepository.save(admin);
        Result r = Result.ok(admin);
        return r;
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
//        adminRepository.save(admin);
        adminRepository.deleteById(admin.getId());
        Result r = Result.ok(admin);
        return r;
    }

    @Override
    public List<AdminUserV> getBundRoleUsers(int rid) {
        List<AdminUserV> r = new ArrayList<AdminUserV>();
        List<LzhAdminUserRoleEntity> bundRoleUsers = adminUserRoleRepository.findByRoleId(Long.valueOf(String.valueOf(rid)));
        for (LzhAdminUserRoleEntity bundUid : bundRoleUsers) {
            LzhAdminEntity u = (LzhAdminEntity) adminRepository.findById(new Long(bundUid.getUserId()).intValue());
            AdminUserV v = new AdminUserV();
            BeanUtils.copyProperties(u, v);
            r.add(v);
        }
        return r;
    }

    @Override
    @Transactional
    public void bundRoleUsers(String[] bundUserIds, int rid) {
        adminUserRoleRepository.deleteByRoleId(new Long(String.valueOf(rid)));
        adminUserRoleRepository.flush();
        List<LzhAdminUserRoleEntity> dbList = new ArrayList<>();
        for (String bundUserId : bundUserIds) {
            LzhAdminUserRoleEntity userRole = new LzhAdminUserRoleEntity();
            LzhAdminEntity admin = adminRepository.findById(Integer.parseInt(bundUserId));
            userRole.setRoleId(rid);
            userRole.setUserId(admin.getId());
            dbList.add(userRole);
        }
        adminUserRoleRepository.saveAll(dbList);
    }

    @Autowired
    LzhAdminRepository adminRepository;
    @Autowired
    LzhAdminUserRoleRepository adminUserRoleRepository;
}
