package com.rjpa.service;

import model.Result;
import vo.MessageAmpqV;

public interface IAmpqMessageService {

    Result getMessageByUUID(String uuid);

    public MessageAmpqV addAmpqMessage(MessageAmpqV vo);

    public void saveAmpqMessage(MessageAmpqV vo, Integer messageStatus);
}
