package br.com.wod.quartz.common.service;

import br.com.wod.quartz.api.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.api.dto.jobinfo.QrtzTriggersDTO;
import br.com.wod.quartz.api.dto.time.DailyDTO;
import br.com.wod.quartz.api.dto.time.HourDTO;
import br.com.wod.quartz.api.dto.time.MinuteDTO;
import br.com.wod.quartz.api.dto.time.SecondDTO;

public interface SchedulerBaseService {

    QrtzJobDetailsDTO getJobInfo();

    QrtzTriggersDTO getTriggerInfo();

    void startJob();

    void resumeJob();

    void pauseJob();

    void deleteJob();

    void dailyJobConfig(DailyDTO dailyDTO);

    void hourJobConfig(HourDTO hourDTO);

    void minuteJobConfig(MinuteDTO minuteDTO);

    void secondJobConfig(SecondDTO secondDTO);

}
