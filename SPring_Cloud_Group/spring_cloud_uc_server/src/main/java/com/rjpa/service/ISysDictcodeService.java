package com.rjpa.service;

import com.rjpa.vo.SysDictV;
import com.rjpa.vo.SysDictV;
import model.Result;

public interface ISysDictcodeService {

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISysDictcodeService
     * @Description: 获取平台全部数据字典，SysDictV
     * @parm
     * @author: drouis
     * @date: 2019/4/13 00:31
     */
    public Result getSysDictcodes();

    /**
     * @return com.rjpa.vo.SysDictV
     * @ClassName: com.rjpa.service.ISysDictcodeService
     * @Description: 通过主键ID获取单一数据字典信息
     * @parm mid 数据字典主键ID
     * @author: drouis
     * @date: 2019/4/13 00:36
     */
    public SysDictV getSysDictcodeByDId(int did);

    /**
     * @return com.rjpa.vo.SysDictV
     * @ClassName: com.rjpa.service.ISysDictcodeService
     * @Description: 使用主键验证数据是否存在，并返回数据
     * @parm id 数据字典主键ID
     * @author: drouis
     * @date: 2019/4/13 00:39
     */
    public SysDictV checkSysDictcodeExistByDId(int mid);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISysDictcodeService
     * @Description: 添加平台数据字典
     * @parm sysDictV
     * @author: drouis
     * @date: 2019/4/13 00:41
     */
    public Result addSysDictcode(SysDictV sysDictV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISysDictcodeService
     * @Description: 修改平台数据字典
     * @parm sysDictV
     * @author: drouis
     * @date: 2019/4/13 00:42
     */
    public Result editSysDictcode(SysDictV sysDictV);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISysDictcodeService
     * @Description: 删除平台数据字典
     * @parm mid 数据字典主键ID
     * @author: drouis
     * @date: 2019/4/13 00:44
     */
    public Result delSysDictcode(int did);

    /**
     * @return model.Result
     * @ClassName: com.rjpa.service.ISysDictcodeService
     * @Description: 获取当前数据字典下的所有子数据标准值
     * @parm dpresent 数据字典父索引
     * @author: drouis
     * @date: 2019/4/13 02:19
     */
    public Result getSubSysDictcodesByDpresent(String dpresent);

    public Result getSysDictcodeGroupByDpresent(String dpresent);
}
