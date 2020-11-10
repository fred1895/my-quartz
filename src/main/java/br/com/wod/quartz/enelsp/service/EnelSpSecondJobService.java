package br.com.wod.quartz.enelsp.service;

import br.com.wod.quartz.dto.TimeDTO;
import br.com.wod.quartz.resource.exception.MySchedulerException;
import br.com.wod.quartz.schedule.TriggerMonitor;
import br.com.wod.quartz.service.SchedulerBaseService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class EnelSpSecondJobService implements SchedulerBaseService {

    @Resource
    private Scheduler scheduler;

    @Autowired
    @Qualifier("enelSecondJob")
    private JobDetail jobDetail;

    @Autowired
    @Qualifier("enelSecondTriggerMonitor")
    private TriggerMonitor triggerMonitor;

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    @Override
    public void startJob() {
        log.info("SCHEDULER - START COMMAND");
        try {
            scheduler.start();
            scheduler.scheduleJob(jobDetail, triggerMonitor.getTrigger());
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    @Override
    public void resumeJob() {
        JobKey jobKey = jobDetail.getKey();
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    @Override
    public void pauseJob() {
        JobKey jobKey = jobDetail.getKey();
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    @Override
    public void deleteJob() {
        JobKey jobKey = jobDetail.getKey();
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    @Override
    public void hourJobConfig(TimeDTO timeDTO) {
        SimpleTrigger trigger = (SimpleTrigger) triggerMonitor.getTrigger();

        TriggerBuilder<SimpleTrigger> triggerBuilder = trigger.getTriggerBuilder();

        Trigger newTrigger = triggerBuilder
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(timeDTO.getHour())
                        .repeatForever())
                .build();

        try {
            scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
        triggerMonitor.setTrigger(newTrigger);
    }

    @Override
    public void minuteJobConfig(TimeDTO timeDTO) {
        SimpleTrigger trigger = (SimpleTrigger) triggerMonitor.getTrigger();

        TriggerBuilder<SimpleTrigger> triggerBuilder = trigger.getTriggerBuilder();

        Trigger newTrigger = triggerBuilder
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(timeDTO.getMinute())
                        .repeatForever())
                .build();

        try {
            scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
        triggerMonitor.setTrigger(newTrigger);
    }

    @Override
    public void secondJobConfig(TimeDTO timeDTO) {
        SimpleTrigger trigger = (SimpleTrigger) triggerMonitor.getTrigger();

        TriggerBuilder<SimpleTrigger> triggerBuilder = trigger.getTriggerBuilder();

        Trigger newTrigger = triggerBuilder
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(timeDTO.getSecond())
                        .repeatForever())
                .build();

        try {
            scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
        triggerMonitor.setTrigger(newTrigger);
    }

    @Override
    public void dailyJobConfig(TimeDTO timeDTO) {
        SimpleTrigger trigger = (SimpleTrigger) triggerMonitor.getTrigger();

        Trigger newTrigger = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(timeDTO.getHour(), timeDTO.getMinute()))
                .build();

        try {
            scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
        triggerMonitor.setTrigger(newTrigger);

    }

}
