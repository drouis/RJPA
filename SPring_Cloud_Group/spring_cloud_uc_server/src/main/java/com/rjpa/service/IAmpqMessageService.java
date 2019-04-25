package com.rjpa.service;

import com.rjpa.vo.MessageAmpqV;
import model.Result;

public interface IAmpqMessageService {

    Result getMessageByUUID(String uuid);

    public void addAmpqMessage(MessageAmpqV vo);

    public void saveAmpqMessage(MessageAmpqV vo, Integer messageStatus);
}
