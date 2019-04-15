package com.rjpa.service;

import com.rjpa.vo.AdminMenuV;
import model.Result;

/**
 * @ClassName: ISystemMenuService
 * @Description: 平台菜单服务类
 * @parm
 * @return
 * @author: drouis
 * @date: 2019/4/13 00:28
 */
public interface ISystemMenuService {
    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemMenuService
     * @Description: 获取平台全部菜单，AdminMenuV
     * @parm
     * @author: drouis
     * @date: 2019/4/13 00:31
     */
    public Result getMenus();

    /**
     * @return com.rjpa.vo.AdminMenuV
     * @ClassName: com.rjpa.service.ISystemMenuService
     * @Description: 通过主键ID获取单一菜单信息
     * @parm mid 菜单主键ID
     * @author: drouis
     * @date: 2019/4/13 00:36
     */
    public AdminMenuV getMenuByMId(int mid);

    /**
     * @return boolean
     * @ClassName: com.rjpa.service.ISystemMenuService
     * @Description: 验证菜单是否在DB中存在
     * @parm name 菜单名称，permission 菜单标记
     * @author: drouis
     * @date: 2019/4/13 00:38
     */
    public boolean checkMenuExist(String name, String permission);

    public boolean checkMenuExist(String name, String permission, int parentid);

    /**
     * @return com.rjpa.vo.AdminMenuV
     * @ClassName: com.rjpa.service.ISystemMenuService
     * @Description: 使用主键验证数据是否存在，并返回数据
     * @parm id 菜单主键ID
     * @author: drouis
     * @date: 2019/4/13 00:39
     */
    public AdminMenuV checkMenuExistByMId(int mid);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemMenuService
     * @Description: 添加平台菜单
     * @parm adminMenuV
     * @author: drouis
     * @date: 2019/4/13 00:41
     */
    public Result addMenu(AdminMenuV adminMenuV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemMenuService
     * @Description: 修改平台菜单
     * @parm adminMenuV
     * @author: drouis
     * @date: 2019/4/13 00:42
     */
    public Result editMenu(AdminMenuV adminMenuV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemMenuService
     * @Description: 删除平台菜单
     * @parm mid 菜单主键ID
     * @author: drouis
     * @date: 2019/4/13 00:44
     */
    public Result delMenu(int mId);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISystemMenuService
     * @Description: 获取当前菜单下的所有子菜单数据
     * @parm mid 菜单主键ID
     * @author: drouis
     * @date: 2019/4/13 02:19
     */
    public Result getSubMenusByMId(int mId);
}
