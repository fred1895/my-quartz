package br.com.wod.quartz.enelsp.service;

import br.com.wod.quartz.dto.JobInfoDTO;
import br.com.wod.quartz.dto.TimeDTO;
import br.com.wod.quartz.schedule.TriggerMonitor;
import br.com.wod.quartz.service.JobsBasicService;
import br.com.wod.quartz.service.SchedulerBaseService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class EnelSpFirstJobService implements SchedulerBaseService {

    @Resource
    private Scheduler scheduler;

    @Autowired
    private JobsBasicService basicService;

    @Autowired
    @Qualifier("enelFirstJob")
    private JobDetail jobDetail;

    @Autowired
    @Qualifier("enelFirstJobTriggerMonitor")
    private TriggerMonitor triggerMonitor;

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    @Override
    public JobInfoDTO getJobInfo() {
        return basicService.getInfo(scheduler, jobDetail, triggerMonitor);
    }

    @Override
    public void startJob() {
        basicService.start(scheduler, jobDetail, triggerMonitor);
    }

    @Override
    public void resumeJob() {
        basicService.resume(scheduler, jobDetail);
    }

    @Override
    public void pauseJob() {
        basicService.pause(scheduler, jobDetail);
    }

    @Override
    public void deleteJob() {
        basicService.delete(scheduler, jobDetail);
    }

    @Override
    public void dailyJobConfig(TimeDTO timeDTO) {
        basicService.dailyConfig(scheduler, triggerMonitor, timeDTO);
    }

    @Override
    public void hourJobConfig(TimeDTO timeDTO) {
        basicService.hourConfig(scheduler, triggerMonitor, timeDTO);
    }

    @Override
    public void minuteJobConfig(TimeDTO timeDTO) {
        basicService.minuteConfig(scheduler, triggerMonitor, timeDTO);
    }

    @Override
    public void secondJobConfig(TimeDTO timeDTO) {
        basicService.secondConfig(scheduler, triggerMonitor, timeDTO);
    }

}
