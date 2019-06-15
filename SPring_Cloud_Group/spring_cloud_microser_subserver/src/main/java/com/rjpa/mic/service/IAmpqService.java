package com.rjpa.mic.service;

import com.rjpa.mic.repository.driverschool.Entity.AmpqMessageEntity;

public interface IAmpqService {
    int updateSMSAmpqMessage(AmpqMessageEntity vo);

    int updateQuartzAmpqMessage(AmpqMessageEntity vo);

    int updateMailAmpqMessage(AmpqMessageEntity vo);

}
