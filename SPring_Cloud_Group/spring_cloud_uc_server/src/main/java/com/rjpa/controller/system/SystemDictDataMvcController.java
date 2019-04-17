package com.rjpa.controller.system;

import anno.Permission;
import com.google.gson.Gson;
import com.rjpa.AuthConfig.vo.User;
import com.rjpa.controller.IndexMvcController;
import com.rjpa.repository.Entity.SysDictcodeEntity;
import com.rjpa.service.ISysDictcodeService;
import com.rjpa.vo.IndexPageMenuV;
import com.rjpa.vo.SysDictV;
import model.Result;
import model.SelectObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SystemDictDataMvcController
 * @Description: 平台数据字典管理
 * @parm
 * @return
 * @author: drouis
 * @date: 2019/4/12 23:56
 */
@Controller
@RequestMapping("/sys")
public class SystemDictDataMvcController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String PAGE_DICT_LIST_PO_NAME = "adminDicts";
    public static final String PAGE_DICT_PO_NAME = "adminDictv";
    private static final ModelAndView dictView = new ModelAndView("/system/dictManager");

    Result errMsg = new Result();
    Gson gson = new Gson();

    @Permission(name = "数据字典列表", permissionName = "local.micoUSC.sys.dict", permissionUrl = "/sys/sysDict_")
    @RequestMapping(value = "/sysDict_{pageCurrent}_{pageSize}_{pageCount}", method = RequestMethod.GET)
    public ModelAndView sysDict_(HttpServletRequest request, @PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount) {
        // TODO 1 读取当前系统中所有的数据字典
        List<SysDictcodeEntity> dicts = (List) sysDictcodeService.getSysDictcodeGroupByDpresent("0").getData();
        SysDictV sysDictV = new SysDictV();
        // TODO 2 取得全部菜单列表作为下拉菜单
        List<SelectObject> pageSelectV = new ArrayList<SelectObject>();
        for (SysDictcodeEntity sysDictcodeEntity : dicts) {
            SelectObject selectObject = new SelectObject();
            selectObject.setSelectText(sysDictcodeEntity.getDmemo());
            selectObject.setSelectValue(String.valueOf(sysDictcodeEntity.getDid()));
            if (sysDictcodeEntity.getDid() == sysDictV.getDid()) {
                selectObject.setSelected("selected");
            }
            pageSelectV.add(selectObject);
        }
        dictView.addObject("bundSysdictSelect", pageSelectV);

        // TODO 3 初始化字典类对象
        dictView.addObject(PAGE_DICT_LIST_PO_NAME, dicts);
        dictView.addObject(PAGE_DICT_PO_NAME, new SysDictV());
        dictView.addObject("errMsg", errMsg);
        // TODO 共通处理
        initCommonDatas(request);
        return dictView;
    }

    @Permission(name = "数据字典初始化", permissionName = "local.micoUSC.sys.initSysMenu", permissionUrl = "/sys/initSysMenu")
    @RequestMapping(value = "/initSysDictcode", method = RequestMethod.GET)
    public ModelAndView initSysDictcode_(HttpServletRequest request, @RequestParam(value = "id") int did) {
        // TODO 1 读取当前系统编辑的数据字典
        SysDictV v = sysDictcodeService.getSysDictcodeByDId(did);
        // TODO 2 读取当前系统数据字典绑定的标准值列表
        List<SysDictcodeEntity> sysDictcodeSubValues = (List) sysDictcodeService.getSubSysDictcodesByDpresent(v.getDpresent()).getData();
        // TODO 3 读取当前系统数据字典作为下拉菜单
        List<SysDictcodeEntity> dicts = (List) sysDictcodeService.getSysDictcodeGroupByDpresent("0").getData();
        List<SelectObject> pageSelectV = new ArrayList<SelectObject>();
        for (SysDictcodeEntity sysDictcodeEntity : dicts) {
            SelectObject selectObject = new SelectObject();
            selectObject.setSelectText(sysDictcodeEntity.getDmemo());
            selectObject.setSelectValue(String.valueOf(sysDictcodeEntity.getDid()));
            if (sysDictcodeEntity.getDid() == v.getDid()) {
                selectObject.setSelected("selected");
            }
            pageSelectV.add(selectObject);
        }
        dictView.addObject("bundSysdictSelect", pageSelectV);
        // TODO 3 初始化字典类对象
        dictView.addObject(PAGE_DICT_LIST_PO_NAME, sysDictcodeSubValues);
        dictView.addObject(PAGE_DICT_PO_NAME, v);
        // TODO 共通处理
        initCommonDatas(request);
        return dictView;
    }

    @Permission(name = "添加数据字典标准值", permissionName = "local.micoUSC.sys.getBundSubMenu", permissionUrl = "/sys/getBundSubMenu")
    @RequestMapping(value = "/getBundSubSysDictcode", method = RequestMethod.GET)
    public ModelAndView getBundSubSysDictcode_(HttpServletRequest request, SysDictV sysDictV) {
        // TODO 共通处理
        initCommonDatas(request);
        return dictView;
    }

    private void initCommonDatas(HttpServletRequest request) {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                .getAttribute("SPRING_SECURITY_CONTEXT");
        User u = (User) securityContextImpl.getAuthentication().getPrincipal();
        List<IndexPageMenuV> menuVS = u.getMenuVS();
        dictView.addObject(IndexMvcController.MENU_REDIS_NAME, menuVS);
        dictView.addObject("admin", u);
    }

    ISysDictcodeService sysDictcodeService;
}
