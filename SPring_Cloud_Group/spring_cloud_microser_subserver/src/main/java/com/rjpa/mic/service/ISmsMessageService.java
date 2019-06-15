package com.rjpa.mic.service;

import model.Result;
import vo.SmsMessageV;

public interface ISmsMessageService {

    public Result saveSmsMessage(SmsMessageV vo);
}
