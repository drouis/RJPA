package com.rjpa.service.impl;

import com.rjpa.repository.Entity.LzhAdminRoleEntity;
import com.rjpa.repository.LzhAdminRoleRepository;
import com.rjpa.service.ISystemRoleService;
import com.rjpa.vo.AdminRoleV;
import model.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class SystemRoleServiceImpl implements ISystemRoleService {

    @Override
    public Result getRoles() {
        return Result.ok(adminRoleRepository.findAll());
    }

    @Override
    public AdminRoleV getRoleByRId(int id) {
        LzhAdminRoleEntity adminDB = (LzhAdminRoleEntity) adminRoleRepository.getOne(id);
        AdminRoleV po = BeanUtils.instantiateClass(AdminRoleV.class);
        if (null != adminDB) {
            BeanUtils.copyProperties(adminDB, po);
        }
        return po;
    }

    @Override
    public boolean checkRoleExist(String name, String role) {
        if (!StringUtils.isEmpty(name)) {
            List checkList = (List) adminRoleRepository.findByName(name);
            if (null != checkList && 0 < checkList.size()) {
                return true;
            }
        }
        if (!StringUtils.isEmpty(role)) {
            List checkList = (List) adminRoleRepository.findByRole(role);
            if (null != checkList && 0 < checkList.size()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AdminRoleV checkRoleExistByRId(int id) {
        LzhAdminRoleEntity roleDB = (LzhAdminRoleEntity) adminRoleRepository.getOne(id);
        AdminRoleV po = BeanUtils.instantiateClass(AdminRoleV.class);
        if (null != roleDB) {
            BeanUtils.copyProperties(roleDB, po);
        }
        return po;
    }

    @Override
    public Result addRole(AdminRoleV adminRoleV) {
        LzhAdminRoleEntity role = new LzhAdminRoleEntity();
        BeanUtils.copyProperties(adminRoleV, role);
        role.setFlg(0);
        adminRoleRepository.save(role);
        Result r = Result.ok(role);
        return r;
    }

    @Override
    public Result editRole(AdminRoleV adminRoleV) {
        LzhAdminRoleEntity role = new LzhAdminRoleEntity();
        BeanUtils.copyProperties(adminRoleV, role);
        adminRoleRepository.save(role);
        Result r = Result.ok(role);
        return r;
    }

    @Override
    public Result forbRole(AdminRoleV adminRoleV) {
        LzhAdminRoleEntity role = new LzhAdminRoleEntity();
        BeanUtils.copyProperties(adminRoleV, role);
        role.setFlg(1);
        adminRoleRepository.save(role);
        Result r = Result.ok(role);
        return r;
    }

    @Override
    public Result actiRole(AdminRoleV adminRoleV) {
        LzhAdminRoleEntity role = new LzhAdminRoleEntity();
        BeanUtils.copyProperties(adminRoleV, role);
        role.setFlg(0);
        adminRoleRepository.save(role);
        Result r = Result.ok(role);
        return r;
    }

    @Override
    public Result delRole(int rId) {
        LzhAdminRoleEntity role = (LzhAdminRoleEntity) adminRoleRepository.findById(rId);
        adminRoleRepository.deleteById(new Long(role.getId()).intValue());
        Result r = Result.ok(role);
        return r;
    }

    @Autowired
    LzhAdminRoleRepository adminRoleRepository;
}
