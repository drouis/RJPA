package com.rjpa.mic.service;

import com.rjpa.mic.repository.driverschool.Entity.AmpqMessageEntity;

public interface IAmpqService {
    void updateSMSAmpqMessage(AmpqMessageEntity vo);

    void updateQuartzAmpqMessage(AmpqMessageEntity vo);

    void updateMailAmpqMessage(AmpqMessageEntity vo);

}
