package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.JobInfoDTO;
import br.com.wod.quartz.dto.TimeDTO;

public interface SchedulerBaseService {

    JobInfoDTO getJobInfo();

    void startJob();

    void resumeJob();

    void pauseJob();

    void deleteJob();

    void dailyJobConfig(TimeDTO timeDTO);

    void hourJobConfig(TimeDTO timeDTO);

    void minuteJobConfig(TimeDTO timeDTO);

    void secondJobConfig(TimeDTO timeDTO);

}
