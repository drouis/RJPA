package com.rjpa.service;

import com.rjpa.vo.MessageAmpqV;
import model.Result;

public interface IAmpqMessageService {

    Result getMessageByUUID(String uuid);

    public MessageAmpqV addAmpqMessage(MessageAmpqV vo);

    public void saveAmpqMessage(MessageAmpqV vo, Integer messageStatus);
}
