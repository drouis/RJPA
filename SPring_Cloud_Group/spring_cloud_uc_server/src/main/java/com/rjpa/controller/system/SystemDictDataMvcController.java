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
import model.utils.SystemConstCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Permission(name = "数据字典列表", permissionName = "local.micoUSC.syss.dict", permissionUrl = "/sys/sysDict_")
    @RequestMapping(value = "/sysDict_{pageCurrent}_{pageSize}_{pageCount}", method = RequestMethod.GET)
    public ModelAndView sysDict_(HttpServletRequest request, @PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount) {
        // TODO 共通处理
        initCommonDatas(request);
        // TODO 1 读取当前系统中所有的数据字典
        List<SysDictcodeEntity> dicts = (List) sysDictcodeService.getSysDictcodeGroupByDpresent("0").getData();
        SysDictV sysDictV = new SysDictV();
        // TODO 2 取得全部数据字典列表作为下拉数据字典
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
        return dictView;
    }

    @Permission(name = "数据字典初始化", permissionName = "local.micoUSC.syss.initSysDictcode", permissionUrl = "/sys/initSysDictcode")
    @RequestMapping(value = "/initSysDictcode", method = RequestMethod.GET)
    public ModelAndView initSysDictcode_(HttpServletRequest request, @RequestParam(value = "did") int did) {
        // TODO 共通处理
        initCommonDatas(request);
        // TODO 1 读取当前系统编辑的数据字典
        SysDictV v = sysDictcodeService.getSysDictcodeByDId(did);
        // TODO 2 读取当前系统数据字典绑定的标准值列表
        List<SysDictcodeEntity> sysDictcodeSubValues = (List) sysDictcodeService.getSubSysDictcodesByDpresent(v.getDpresent()).getData();
        // TODO 3 读取当前系统数据字典作为下拉数据字典
        List<SysDictcodeEntity> dicts = (List) sysDictcodeService.getSysDictcodeGroupByDpresent("0").getData();
        List<SelectObject> pageSelectV = new ArrayList<SelectObject>();
        for (SysDictcodeEntity sysDictcodeEntity : dicts) {
            SelectObject selectObject = new SelectObject();
            selectObject.setSelectText(sysDictcodeEntity.getDmemo());
            selectObject.setSelectValue(String.valueOf(sysDictcodeEntity.getDid()));
            if ((!v.getDpresent().equals("0")) && sysDictcodeEntity.getDid() == v.getDid()) {
                selectObject.setSelected("selected");
            }
            pageSelectV.add(selectObject);
        }
        dictView.addObject("bundSysdictSelect", pageSelectV);
        // TODO 3 初始化字典类对象
        dictView.addObject(PAGE_DICT_LIST_PO_NAME, sysDictcodeSubValues);
        dictView.addObject(PAGE_DICT_PO_NAME, v);
        return dictView;
    }

    @Permission(name = "数据字典标准值", permissionName = "local.micoUSC.syss.getBundSubSysDictcode", permissionUrl = "/sys/getBundSubSysDictcode")
    @RequestMapping(value = "/getBundSubSysDictcode", method = RequestMethod.GET)
    public ModelAndView getBundSubSysDictcode_(HttpServletRequest request, SysDictV sysDictV) {
        // TODO 共通处理
        initCommonDatas(request);
        // TODO 1 获取选中的字典数据
        SysDictV v = sysDictcodeService.getSysDictcodeByDId(sysDictV.getDid());
        v.setDid(0);
        // TODO 2 拉取字典中的子数据列表
        List<SysDictcodeEntity> sysDictcodeSubValues = (List) sysDictcodeService.getSubSysDictcodesByDpresent(v.getDcolumn()).getData();
        // TODO 3 读取当前系统数据字典作为下拉数据字典
        List<SysDictcodeEntity> dicts = (List) sysDictcodeService.getSysDictcodeGroupByDpresent("0").getData();
        List<SelectObject> pageSelectV = new ArrayList<SelectObject>();
        for (SysDictcodeEntity sysDictcodeEntity : dicts) {
            SelectObject selectObject = new SelectObject();
            selectObject.setSelectText(sysDictcodeEntity.getDmemo());
            selectObject.setSelectValue(String.valueOf(sysDictcodeEntity.getDid()));
            if (sysDictcodeEntity.getDcolumn() == v.getDpresent()) {
                selectObject.setSelected("selected");
            }
            pageSelectV.add(selectObject);
        }
        dictView.addObject("bundSysdictSelect", pageSelectV);
        // TODO 3 初始化字典类对象
        dictView.addObject(PAGE_DICT_LIST_PO_NAME, sysDictcodeSubValues);
        dictView.addObject(PAGE_DICT_PO_NAME, v);
        return dictView;
    }

    /**
     * 添加系统数据字典
     */
    @Permission(name = "添加系统数据字典", permissionName = "local.micoUSC.syss.addSysDictcode", permissionUrl = "/sys/addSysDictcode")
    @RequestMapping(value = "/addSysDictcode", method = RequestMethod.POST)
    public ModelAndView addSysRole_(HttpServletRequest request, SysDictV sysDictV, @RequestParam(value = "bundSysdictcodeSelect") String bundSysdictcodeSelect) {
        // TODO 共通处理
        initCommonDatas(request);
        if (!StringUtils.isEmpty(bundSysdictcodeSelect)) {
            SysDictV pSysdictcode = sysDictcodeService.getSysDictcodeByDId(Integer.parseInt(bundSysdictcodeSelect));
            sysDictV.setDpresent(pSysdictcode.getDcolumn());
        }
        // TODO 1 数据字典信息验证
        if (sysDictV.getDid() == 0) {
            // TODO 1.1 登陆名重复验证,绑定手机验证
            boolean checkFlg = sysDictcodeService.checkSysDictcodeExist(sysDictV.getDcolumn());
            try {
                // TODO 2 添加系统数据字典
                if (!checkFlg) {
                    sysDictcodeService.addSysDictcode(sysDictV);
                } else {
                    errMsg = Result.error(SystemConstCode.MENU_IS_EXIST.getCode(), SystemConstCode.MENU_IS_EXIST.getMessage());
                }
            } catch (Exception e) {
                errMsg = Result.error(SystemConstCode.ERROR.getMessage());
            }
            return new ModelAndView("redirect:/sys/sysDict_0_0_0");
        } else {
            // TODO 1.1 验证子数据字典是否存在
            boolean checkFlg = sysDictcodeService.checkSysDictcodeExist(sysDictV.getDcolumn());
            try {
                // TODO 数据字典存在判定，数据存在则通过验证
                if (checkFlg) {
                    if (StringUtils.isEmpty(sysDictV.getDpresent())) {
                        sysDictV.setDpresent("0");
                    }
                    // TODO 2 更新系统子数据字典
                    sysDictcodeService.editSysDictcode(sysDictV);
                    // TODO 3 判定是否存在字典标准值，如果存在则更新标准值
                    List<SysDictcodeEntity> subDictcodes = (List<SysDictcodeEntity>) sysDictcodeService.getSubSysDictcodesByDpresent(sysDictV.getDcolumn()).getData();
                    if (null != subDictcodes && 0 < subDictcodes.size()) {
                        for (SysDictcodeEntity subDictcode : subDictcodes) {
                            subDictcode.setDpresent(sysDictV.getDcolumn());
                            SysDictV subV = new SysDictV();
                            BeanUtils.copyProperties(subDictcode, subV);
                            sysDictcodeService.editSysDictcode(subV);
                        }
                    }
                    return new ModelAndView("redirect:/sys/getBundSubSysDictcode?did=" + sysDictV.getDid());
                } else {
                    errMsg = Result.error(SystemConstCode.MENU_IS_EXIST.getCode(), SystemConstCode.MENU_IS_EXIST.getMessage());
                }
            } catch (Exception e) {
                errMsg = Result.error(SystemConstCode.ERROR.getMessage());
            }
            // TODO 2 读取当前系统中所有的绑定数据字典
            List<SysDictcodeEntity> sysDictcodeSubValues = (List) sysDictcodeService.getSysDictcodeGroupByDpresent(sysDictV.getDcolumn()).getData();
            List<SysDictcodeEntity> dicts = (List) sysDictcodeService.getSysDictcodeGroupByDpresent("0").getData();
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
            dictView.addObject(PAGE_DICT_LIST_PO_NAME, sysDictcodeSubValues);
            dictView.addObject(PAGE_DICT_PO_NAME, sysDictV);
        }
        dictView.addObject("errMsg", errMsg);
        return dictView;
    }

    private void initCommonDatas(HttpServletRequest request) {
        errMsg = new Result();
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                .getAttribute("SPRING_SECURITY_CONTEXT");
        User u = (User) securityContextImpl.getAuthentication().getPrincipal();
        List<IndexPageMenuV> menuVS = u.getMenuVS();
        dictView.addObject(IndexMvcController.MENU_REDIS_NAME, menuVS);
        dictView.addObject("admin", u);
        dictView.addObject("errMsg", errMsg);
    }

    @Autowired
    ISysDictcodeService sysDictcodeService;
}
