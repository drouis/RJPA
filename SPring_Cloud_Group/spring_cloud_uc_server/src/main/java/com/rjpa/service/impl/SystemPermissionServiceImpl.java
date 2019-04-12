package com.rjpa.service.impl;

import com.rjpa.repository.Entity.LzhAdminPermissionEntity;
import com.rjpa.repository.Entity.LzhAdminRolePermissionEntity;
import com.rjpa.repository.LzhAdminPermissionRepository;
import com.rjpa.repository.LzhAdminRolePermissionRepository;
import com.rjpa.service.ISystemPermissionService;
import com.rjpa.vo.AdminPermissionV;
import model.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemPermissionServiceImpl implements ISystemPermissionService {
    @Override
    public Result getPermissions() {
        return Result.ok(adminPermissionRepository.findAll());
    }

    @Override
    public AdminPermissionV getPermissionByRId(int id) {
        LzhAdminPermissionEntity permissionDB = (LzhAdminPermissionEntity) adminPermissionRepository.getOne(id);
        AdminPermissionV po = BeanUtils.instantiateClass(AdminPermissionV.class);
        if (null != permissionDB) {
            BeanUtils.copyProperties(permissionDB, po);
        }
        return po;
    }

    @Override
    public boolean checkPermissionExist(String name, String permission) {
        if (!StringUtils.isEmpty(name)) {
            List checkList = (List) adminPermissionRepository.findByName(name);
            if (null != checkList && 0 < checkList.size()) {
                return true;
            }
        }
        if (!StringUtils.isEmpty(permission)) {
            List checkList = (List) adminPermissionRepository.findByPermission(permission);
            if (null != checkList && 0 < checkList.size()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AdminPermissionV checkPermissionExistByUId(int id) {
        LzhAdminPermissionEntity adminDB = (LzhAdminPermissionEntity) adminPermissionRepository.getOne(id);
        AdminPermissionV po = BeanUtils.instantiateClass(AdminPermissionV.class);
        if (null != adminDB) {
            BeanUtils.copyProperties(adminDB, po);
        }
        return po;
    }

    @Override
    public Result addPermission(AdminPermissionV adminPermissionV) {
        LzhAdminPermissionEntity permission = new LzhAdminPermissionEntity();
        BeanUtils.copyProperties(adminPermissionV, permission);
        adminPermissionRepository.save(permission);
        Result r = Result.ok(permission);
        return r;
    }

    @Override
    public Result editPermission(AdminPermissionV adminPermissionV) {
        LzhAdminPermissionEntity permission = new LzhAdminPermissionEntity();
        BeanUtils.copyProperties(adminPermissionV, permission);
        adminPermissionRepository.save(permission);
        Result r = Result.ok(permission);
        return r;
    }

    @Override
    public Result delPermission(int permissionId) {
        LzhAdminPermissionEntity permission = (LzhAdminPermissionEntity) adminPermissionRepository.findById(permissionId);
        adminPermissionRepository.deleteById(new Long(permission.getId()).intValue());
        Result r = Result.ok(permission);
        return r;
    }

    @Override
    public List<AdminPermissionV> getBundRolePermissions(int rid) {
        List<AdminPermissionV> r = new ArrayList<AdminPermissionV>();
        List<LzhAdminRolePermissionEntity> bundRolePermissions = adminRolePermissionRepository.findByRoleId(Long.valueOf(String.valueOf(rid)));
        for (LzhAdminRolePermissionEntity bundPermissionId : bundRolePermissions) {
            try {
                LzhAdminPermissionEntity p = (LzhAdminPermissionEntity) adminPermissionRepository.findById(new Long(bundPermissionId.getPermissionId()).intValue());
                AdminPermissionV v = new AdminPermissionV();
                BeanUtils.copyProperties(p, v);
                r.add(v);
            } catch (Exception e) {
                System.out.println(bundPermissionId.getId() + bundPermissionId.getPermissionId() + bundPermissionId.getRoleId());
            }
        }
        return r;
    }

    @Override
    @Transactional
    public void bundRolePermissions(String[] bundPermissionIds, int rid) {
        adminRolePermissionRepository.deleteAllByRoleId(new Long(String.valueOf(rid)));
        adminRolePermissionRepository.flush();
        List<LzhAdminRolePermissionEntity> dbList = new ArrayList<>();
        for (String bundPermissionId : bundPermissionIds) {
            LzhAdminRolePermissionEntity rolePermission = new LzhAdminRolePermissionEntity();
            LzhAdminPermissionEntity permission = adminPermissionRepository.findById(Integer.parseInt(bundPermissionId));
            rolePermission.setRoleId(new Long(rid));
            rolePermission.setPermissionId(permission.getId());
            dbList.add(rolePermission);
        }
        adminRolePermissionRepository.saveAll(dbList);
    }

    @Autowired
    LzhAdminPermissionRepository adminPermissionRepository;
    @Autowired
    LzhAdminRolePermissionRepository adminRolePermissionRepository;

}
