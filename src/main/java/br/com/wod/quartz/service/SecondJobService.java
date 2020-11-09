package br.com.wod.quartz.service;

import br.com.wod.quartz.schedule.TriggerMonitor;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SecondJobService {

    @Resource
    private Scheduler scheduler;

    @Resource
    private JobDetail secondJobDetail;

    @Resource
    private TriggerMonitor secondTriggerMonitor;

    public void startSecondJob() throws SchedulerException {
        scheduler.start();
        scheduler.scheduleJob(secondJobDetail, secondTriggerMonitor.getTrigger());
    }
}
