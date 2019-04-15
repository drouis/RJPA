package com.rjpa.service.impl;

import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;
import com.rjpa.repository.Entity.LzhAdminPermissionEntity;
import com.rjpa.repository.LzhAdminMenusRightsRepository;
import com.rjpa.repository.LzhAdminPermissionRepository;
import com.rjpa.service.ISystemMenuService;
import com.rjpa.vo.AdminMenuV;
import model.Result;
import model.utils.SystemConstCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemMenuServiceImpl implements ISystemMenuService {

    public static final String MENU_PERMISSION_DESCRIPTION_STR = "menu.permission";

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
        // TODO 1 更新系统菜单表数据
        {
            LzhAdminMenusRightsEntity menu = new LzhAdminMenusRightsEntity();
            //
            try {
                BeanUtils.copyProperties(adminMenuV, menu);
                adminMenusRightsRepository.save(menu);
            } catch (Exception e) {
                return Result.error(SystemConstCode.ERROR.getMessage());
            }
        }
        // TODO 2 更新系统权限表数据
        {
            LzhAdminPermissionEntity permissionEntity = new LzhAdminPermissionEntity();
            try {
                //TODO 2.1 根据菜单权限内容取得权限表中久历史数据，并删除历史数据
                List<LzhAdminPermissionEntity> pl = adminPermissionRepository.findByPermission(adminMenuV.getPermission());
                if (null != pl && 0 < pl.size()) {
                    LzhAdminPermissionEntity oldPermissionDBData = pl.get(0);
                    // TODO 2.2 删除历史权限数据
                    adminPermissionRepository.deleteById(new Long(oldPermissionDBData.getId()).intValue());
                    adminMenusRightsRepository.flush();
                }
                // TODO 2.3 插入新的权限数据
                LzhAdminPermissionEntity newPermissionDBData = new LzhAdminPermissionEntity();
                newPermissionDBData.setDescription(MENU_PERMISSION_DESCRIPTION_STR);
                newPermissionDBData.setName(adminMenuV.getName());
                newPermissionDBData.setPermission(adminMenuV.getPermission());
                newPermissionDBData.setPermissionUrl(adminMenuV.getUrl());
                adminPermissionRepository.save(newPermissionDBData);

            } catch (Exception e) {
                return Result.error(SystemConstCode.ERROR.getMessage());
            }
        }
        // TODO 3 返回菜单数据
        Result r = Result.ok(adminMenuV);
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
        // TODO 删除菜单数据
        LzhAdminMenusRightsEntity menu = (LzhAdminMenusRightsEntity) adminMenusRightsRepository.findById(mId);
        List<LzhAdminMenusRightsEntity> subMenus = adminMenusRightsRepository.findByParentidOrderByClassesAsc(mId);
        if (null == subMenus || subMenus.size() == 0) {
            adminMenusRightsRepository.deleteById(menu.getId());
        } else {
            return Result.error(SystemConstCode.MENU_SUBMENUS_EXIST_ERROR.getCode(), SystemConstCode.MENU_SUBMENUS_EXIST_ERROR.getMessage());
        }
        // TODO 删除权限数据
        List<LzhAdminPermissionEntity> pl = adminPermissionRepository.findByPermission(menu.getPermission());
        if (null != pl && 0 < pl.size()) {
            LzhAdminPermissionEntity oldPermissionDBData = pl.get(0);
            // TODO 2.2 删除历史权限数据
            adminPermissionRepository.deleteById(new Long(oldPermissionDBData.getId()).intValue());
            adminMenusRightsRepository.flush();
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
                LzhAdminMenusRightsEntity pdb = adminMenusRightsRepository.findById(db.getParentid());
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
    @Autowired
    LzhAdminPermissionRepository adminPermissionRepository;
}
