package com.rjpa.mic.service.impl;

import com.google.gson.Gson;
import com.rjpa.mic.repository.driverschool.Entity.AmpqMessageEntity;
import com.rjpa.mic.service.IAmpqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import vo.MessageAmpqV;

import java.util.List;

@Service
public class AmpqServiceImpl implements IAmpqService {
    Gson gson = new Gson();
    private String searchSQL = "SELECT * FROM ampq_message WHERE uuid=? AND ampq_statue =" + MessageAmpqV.getMessageUnRead();
    private String updateSQL = "UPDATE ampq_message SET ampq_statue=? WHERE uuid=?";

    @Override
    public int updateSMSAmpqMessage(AmpqMessageEntity vo) {
        List dbdata = driverSchoolJdbcTemplate.queryForList(searchSQL, new Object[]{vo.getUuid()});
        if (null != dbdata && 0 < dbdata.size()) {
            return driverSchoolJdbcTemplate.update(updateSQL, new Object[]{MessageAmpqV.getMessageUnRead(), vo.getUuid()});
        }
        return 0;
    }

    @Override
    public int updateQuartzAmpqMessage(AmpqMessageEntity vo) {
        List dbdata = driverSchoolJdbcTemplate.queryForList(searchSQL, new Object[]{vo.getUuid()});
        if (null != dbdata && 0 < dbdata.size()) {
            return driverSchoolJdbcTemplate.update(updateSQL, new Object[]{MessageAmpqV.getMessageUnRead(), vo.getUuid()});
        }
        return 0;
    }

    @Override
    public int updateMailAmpqMessage(AmpqMessageEntity vo) {
        List dbdata = driverSchoolJdbcTemplate.queryForList(searchSQL, new Object[]{vo.getUuid()});
        if (null != dbdata && 0 < dbdata.size()) {
            return driverSchoolJdbcTemplate.update(updateSQL, new Object[]{MessageAmpqV.getMessageUnRead(), vo.getUuid()});
        }
        return 0;
    }

    @Autowired
    @Qualifier("driverSchoolJdbcTemplate")
    protected JdbcTemplate driverSchoolJdbcTemplate;
}
