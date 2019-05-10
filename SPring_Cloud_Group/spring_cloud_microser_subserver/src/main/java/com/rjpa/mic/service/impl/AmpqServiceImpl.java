package com.rjpa.mic.service.impl;

import com.google.gson.Gson;
import com.rjpa.mic.repository.driverschool.Entity.AmpqMessageEntity;
import com.rjpa.mic.service.IAmpqService;
import com.rjpa.vo.MessageAmpqV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmpqServiceImpl implements IAmpqService {
    Gson gson = new Gson();
    private String searchSQL = "select * from ampq_message where uuid=?";
    private String updateSQL = "update ampq_message set ampqStatue=? where uuid=?";

    @Override
    public void updateSMSAmpqMessage(AmpqMessageEntity vo) {
        List dbdata = driverSchoolJdbcTemplate.queryForList(searchSQL, new Object[]{vo.getUuid()});
        if (null != dbdata && 0 < dbdata.size()) {
            driverSchoolJdbcTemplate.update(updateSQL, new Object[]{MessageAmpqV.getMessageUnRead(), vo.getUuid()});
        }
    }

    @Override
    public void updateQuartzAmpqMessage(AmpqMessageEntity vo) {
        List dbdata = driverSchoolJdbcTemplate.queryForList(searchSQL, new Object[]{vo.getUuid()});
        if (null != dbdata && 0 < dbdata.size()) {
            driverSchoolJdbcTemplate.update(updateSQL, new Object[]{MessageAmpqV.getMessageUnRead(), vo.getUuid()});
        }
    }

    @Override
    public void updateMailAmpqMessage(AmpqMessageEntity vo) {
        List dbdata = driverSchoolJdbcTemplate.queryForList(searchSQL, new Object[]{vo.getUuid()});
        if (null != dbdata && 0 < dbdata.size()) {
            driverSchoolJdbcTemplate.update(updateSQL, new Object[]{MessageAmpqV.getMessageUnRead(), vo.getUuid()});
        }
    }

    @Autowired
    @Qualifier("driverSchoolJdbcTemplate")
    protected JdbcTemplate driverSchoolJdbcTemplate;
}
