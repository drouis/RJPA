package com.rjpa.service.impl;

import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;
import com.rjpa.repository.LzhAdminMenusRightsRepository;
import com.rjpa.service.ISystemMenuService;
import com.rjpa.vo.AdminMenuV;
import model.Result;
import model.utils.SystemConstCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemMenuServiceImpl implements ISystemMenuService {
    @Override
    public Result getMenus() {
        List<AdminMenuV> vl = new ArrayList<AdminMenuV>();
        List<LzhAdminMenusRightsEntity> dbList = adminMenusRightsRepository.findAll();
        for (LzhAdminMenusRightsEntity db : dbList) {
            AdminMenuV v = new AdminMenuV();
            BeanUtils.copyProperties(db, v);
            if (db.getParentid() != -1) {
                LzhAdminMenusRightsEntity pdb = adminMenusRightsRepository.getOne(db.getParentid());
                v.setParentMenuName(pdb.getName());
            } else {
                v.setParentMenuName("");
            }
            vl.add(v);
        }
        return Result.ok(vl);
    }

    @Override
    public AdminMenuV getMenuByMId(int mid) {
        LzhAdminMenusRightsEntity menuDB = (LzhAdminMenusRightsEntity) adminMenusRightsRepository.getOne(mid);
        AdminMenuV po = BeanUtils.instantiateClass(AdminMenuV.class);
        if (null != menuDB) {
            BeanUtils.copyProperties(menuDB, po);
        }
        return po;
    }

    @Override
    public boolean checkMenuExist(String name, String permission) {
        List checkList = (List) adminMenusRightsRepository.findByParentidAndNameAndPermissionOrderByClassesAsc(-1, name, permission);
        if (null != checkList && 0 < checkList.size()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkMenuExist(String name, String permission, int parentid) {
        List checkList = (List) adminMenusRightsRepository.findByParentidAndNameAndPermissionOrderByClassesAsc(parentid, name, permission);
        if (null != checkList && 0 < checkList.size()) {
            return true;
        }
        return false;
    }

    @Override
    public AdminMenuV checkMenuExistByMId(int id) {
        LzhAdminMenusRightsEntity menuDB = (LzhAdminMenusRightsEntity) adminMenusRightsRepository.getOne(id);
        AdminMenuV po = BeanUtils.instantiateClass(AdminMenuV.class);
        if (null != menuDB) {
            BeanUtils.copyProperties(menuDB, po);
        }
        return po;
    }

    @Transactional
    @Override
    public Result addMenu(AdminMenuV adminMenuV) {
        LzhAdminMenusRightsEntity menu = new LzhAdminMenusRightsEntity();
        BeanUtils.copyProperties(adminMenuV, menu);
        adminMenusRightsRepository.save(menu);
        Result r = Result.ok(menu);
        return r;
    }

    @Transactional
    @Override
    public Result editMenu(AdminMenuV adminMenuV) {
        LzhAdminMenusRightsEntity menu = new LzhAdminMenusRightsEntity();
        BeanUtils.copyProperties(adminMenuV, menu);
        adminMenusRightsRepository.save(menu);
        Result r = Result.ok(menu);
        return r;
    }

    @Transactional
    @Override
    public Result delMenu(int mId) {
        LzhAdminMenusRightsEntity menu = (LzhAdminMenusRightsEntity) adminMenusRightsRepository.findById(mId);
        List<LzhAdminMenusRightsEntity> subMenus = adminMenusRightsRepository.findByParentidOrderByClassesAsc(mId);
        if (null == subMenus || subMenus.size() == 0) {
            adminMenusRightsRepository.deleteById(menu.getId());
        } else {
            return Result.error(SystemConstCode.MENU_SUBMENUS_EXIST_ERROR.getCode(), SystemConstCode.MENU_SUBMENUS_EXIST_ERROR.getMessage());
        }
        Result r = Result.ok(menu);
        return r;
    }

    @Override
    public Result getSubMenusByMId(int mId) {
        List<AdminMenuV> vl = new ArrayList<AdminMenuV>();
        List<LzhAdminMenusRightsEntity> dbList = adminMenusRightsRepository.findByParentidOrderByClassesAsc(mId);
        for (LzhAdminMenusRightsEntity db : dbList) {
            AdminMenuV v = new AdminMenuV();
            BeanUtils.copyProperties(db, v);
            if (db.getParentid() != -1) {
                LzhAdminMenusRightsEntity pdb = adminMenusRightsRepository.getOne(db.getParentid());
                v.setParentMenuName(pdb.getName());
            } else {
                v.setParentMenuName("");
            }
            vl.add(v);
        }
        return Result.ok(vl);
    }

    @Autowired
    LzhAdminMenusRightsRepository adminMenusRightsRepository;
}
