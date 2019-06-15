package com.rjpa.mic.service.impl;

import com.rjpa.feign.client.SystemToolsServiceFeignClient;
import com.rjpa.mic.service.ISmsMessageService;
import model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vo.SmsMessageV;

import java.util.Date;

@Service
public class SmsMessageServiceImpl implements ISmsMessageService {

    @Override
    public Result saveSmsMessage(SmsMessageV vo) {
        vo.setSendTime(new Date());
        // 保存用户发送的短信数据
        systemToolsServiceFeignClient.postSaveUserSendedSMSFeignAPI(vo);
        return Result.ok(vo);
    }

    @Autowired
    SystemToolsServiceFeignClient systemToolsServiceFeignClient;
}
