package br.com.wod.quartz.core.adapters;

import br.com.wod.quartz.api.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.api.dto.jobinfo.QrtzTriggersDTO;

public interface IJobsInfoService {
    QrtzJobDetailsDTO getJobInfo();

    QrtzTriggersDTO getTriggerInfo();
}
