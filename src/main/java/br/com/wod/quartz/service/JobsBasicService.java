package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.JobInfoDTO;
import br.com.wod.quartz.dto.TimeDTO;
import br.com.wod.quartz.resource.exception.MySchedulerException;
import br.com.wod.quartz.schedule.TriggerMonitor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.TimeZone;

import static br.com.wod.quartz.service.JobsBasicServiceUtil.*;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@Slf4j
public class JobsBasicService {

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    public JobInfoDTO getInfo(Scheduler scheduler, JobDetail jobDetail, TriggerMonitor triggerMonitor) {
        JobInfoDTO jobInfo = new JobInfoDTO();

        try {
            JobDetailImpl jobDetailImpl = (JobDetailImpl) scheduler
                    .getJobDetail(jobDetail.getKey());

            Trigger trigger = getTriggerFromScheduler(scheduler, triggerMonitor);

            boolean simpleTrigger = isSimpleTrigger(trigger);

            if (simpleTrigger) {
                jobInfo = getSimpleJobDetail(scheduler, triggerMonitor, jobDetailImpl, jobInfo);

            } else {
                jobInfo = getCronJobDetail(scheduler, triggerMonitor, jobDetailImpl, jobInfo);
            }
            return jobInfo;

        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void start(
            Scheduler scheduler,
            JobDetail jobDetail,
            TriggerMonitor triggerMonitor) {
        log.info("SCHEDULER - START COMMAND");
        try {
            scheduler.start();
            scheduler.scheduleJob(jobDetail, triggerMonitor.getTrigger());
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void resume(Scheduler scheduler, JobDetail jobDetail) {
        JobKey jobKey = jobDetail.getKey();
        log.info("SCHEDULER - RESUME COMMAND");
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void pause(Scheduler scheduler, JobDetail jobDetail) {
        JobKey jobKey = jobDetail.getKey();
        log.info("SCHEDULER - PAUSE COMMAND");
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void delete(Scheduler scheduler, JobDetail jobDetail) {
        JobKey jobKey = jobDetail.getKey();
        log.info("SCHEDULER - DELETE COMMAND");
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void dailyConfig(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor,
            TimeDTO timeDTO) {
        SimpleTrigger trigger = (SimpleTrigger) triggerMonitor.getTrigger();

        TriggerBuilder<SimpleTrigger> triggerBuilder = trigger.getTriggerBuilder();

        try {
            SimpleTriggerImpl jobTrigger = (SimpleTriggerImpl) scheduler
                    .getTrigger(triggerMonitor.getTrigger().getKey());

            JobKey jobKey = jobTrigger.getJobKey();

            Trigger newTrigger = newTrigger()
                    .withIdentity("daily")
                    .withSchedule(dailyAtHourAndMinute(timeDTO.getHour(), timeDTO.getMinute())) // execute job daily at 11:30
                    .build();

            scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
            triggerMonitor.setTrigger(newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }

    }

    public void hourConfig(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor,
            TimeDTO timeDTO) {

        log.info("SCHEDULER - HOUR CONFIG COMMAND");
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

    public void minuteConfig(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor,
            TimeDTO timeDTO) {

        log.info("SCHEDULER - MINUTE CONFIG COMMAND");
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

    public void secondConfig(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor,
            TimeDTO timeDTO) {

        log.info("SCHEDULER - SECOND CONFIG COMMAND");
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

}
