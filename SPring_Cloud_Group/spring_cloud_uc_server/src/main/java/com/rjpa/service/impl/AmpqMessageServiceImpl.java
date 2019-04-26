package com.rjpa.service.impl;

import com.rjpa.repository.AmpqMessageRepository;
import com.rjpa.repository.Entity.AmpqMessageEntity;
import com.rjpa.service.IAmpqMessageService;
import com.rjpa.vo.MessageAmpqV;
import model.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Service
public class AmpqMessageServiceImpl implements IAmpqMessageService {
    @Override
    public void addAmpqMessage(MessageAmpqV vo) {
        AmpqMessageEntity dbData = new AmpqMessageEntity();
        BeanUtils.copyProperties(vo, dbData);
        dbData.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
        dbData.setAmpqStatue(vo.getMessageUnSend());
        dbData.setAmpqTIme(new Timestamp(System.currentTimeMillis()));
        ampqMessageRepository.save(dbData);
    }

    /**
     * @return 无
     * @ClassName: com.rjpa.service.impl.AmpqMessageServiceImpl
     * @Description: 系统消息推送历史日志
     * @parm MessageAmpqV 系统消息数据
     * @author: drouis
     * @date: 2019/4/25 22:59
     */
    @Override
    public void saveAmpqMessage(MessageAmpqV vo, Integer messageStatus) {
        AmpqMessageEntity dbData = new AmpqMessageEntity();
        BeanUtils.copyProperties(vo, dbData);
        dbData.setAmpqStatue(messageStatus);
        ampqMessageRepository.save(dbData);
    }

    @Override
    public Result getMessageByUUID(String uuid) {
        AmpqMessageEntity message = ampqMessageRepository.findByUuid(uuid);
        return Result.ok(message);
    }

    @Autowired
    AmpqMessageRepository ampqMessageRepository;
}
