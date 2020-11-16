package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.jobinfo.JobInfo;
import br.com.wod.quartz.dto.jobinfo.TriggerInfo;
import br.com.wod.quartz.dto.time.DailyDTO;
import br.com.wod.quartz.dto.time.HourDTO;
import br.com.wod.quartz.dto.time.MinuteDTO;
import br.com.wod.quartz.dto.time.SecondDTO;

public interface SchedulerBaseService {


    JobInfo getJobInfo();
    TriggerInfo getTriggerInfo();

    void startJob();

    void resumeJob();

    void pauseJob();

    void deleteJob();

    void dailyJobConfig(DailyDTO dailyDTO);

    void hourJobConfig(HourDTO hourDTO);

    void minuteJobConfig(MinuteDTO minuteDTO);

    void secondJobConfig(SecondDTO secondDTO);

}
