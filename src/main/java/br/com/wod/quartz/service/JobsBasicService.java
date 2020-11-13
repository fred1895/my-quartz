package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.JobInfoBasic;
import br.com.wod.quartz.dto.TimeDTO;
import br.com.wod.quartz.resource.exception.MySchedulerException;
import br.com.wod.quartz.schedule.TriggerMonitor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static br.com.wod.quartz.service.JobsBasicServiceUtil.*;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@Slf4j
public class JobsBasicService {

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    public JobInfoBasic getInfo(Scheduler scheduler, JobDetail jobDetail, TriggerMonitor triggerMonitor) {

        try {
            JobDetailImpl jobDetailImpl = (JobDetailImpl) scheduler
                    .getJobDetail(jobDetail.getKey());

            Trigger trigger = getTriggerFromScheduler(scheduler, triggerMonitor);

            boolean simpleTrigger = isSimpleTrigger(trigger);

            if (simpleTrigger) {
                return getSimpleJobDetail(scheduler, triggerMonitor, jobDetailImpl);

            } else {
                return getCronJobDetail(scheduler, triggerMonitor, jobDetailImpl);
            }


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

        try {
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

        Trigger newTrigger = newTrigger()
                .withIdentity("hour")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(timeDTO.getHour())
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
        Trigger newTrigger = newTrigger()
                .withIdentity("minute")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(timeDTO.getMinute())
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

        Trigger newTrigger = newTrigger()
                .withIdentity("second")
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
