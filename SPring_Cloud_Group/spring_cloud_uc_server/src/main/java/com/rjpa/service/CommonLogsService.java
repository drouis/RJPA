package com.rjpa.service;

import com.rjpa.repository.Entity.LzhAdminLoginLogEntity;
import com.rjpa.repository.LzhAdminLoginLogRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Service
public class CommonLogsService {

    @RequestMapping(method = RequestMethod.POST, value = "/add/optLogs")
    public void insertOperLogByUser(@RequestParam(value = "loginname") String loginname) {
        // 查询旧的历史登陆数据
        LzhAdminLoginLogEntity dbData = lzhAdminLoginLogRepository.getLzhAdminLoginLogEntityByLoginnameEquals(loginname);
        if (null == dbData) {
            dbData = new LzhAdminLoginLogEntity();
            dbData.setLogintime(new Timestamp(System.currentTimeMillis()));
            dbData.setLoginname(loginname);
        }
        // 更新数据内容
        LzhAdminLoginLogEntity updata = new LzhAdminLoginLogEntity();
        BeanUtils.copyProperties(dbData, updata);
        updata.setLogincount(dbData.getLogincount() + 1);
        // 插入数据
        lzhAdminLoginLogRepository.save(updata);
    }

    @Autowired
    LzhAdminLoginLogRepository lzhAdminLoginLogRepository;

}
