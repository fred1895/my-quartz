package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.jobinfo.JobInfo;
import br.com.wod.quartz.dto.jobinfo.TriggerInfo;
import br.com.wod.quartz.dto.time.DailyDTO;
import br.com.wod.quartz.dto.time.HourDTO;
import br.com.wod.quartz.dto.time.MinuteDTO;
import br.com.wod.quartz.dto.time.SecondDTO;
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

    public JobInfo getJobInfo(Scheduler scheduler, JobDetail jobDetail) {
        try {
            JobInfo jobInfo = new JobInfo();
            JobDetailImpl jobDetailImpl = (JobDetailImpl) scheduler
                    .getJobDetail(jobDetail.getKey());

            jobInfo.setJobName(jobDetailImpl.getName());
            jobInfo.setJobGroup(jobDetailImpl.getGroup());
            jobInfo.setJobDescription(jobDetailImpl.getDescription());

            String status = getJobStatus(scheduler);
            jobInfo.setStatus(status);

            return jobInfo;
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public TriggerInfo getTriggerInfo(Scheduler scheduler, TriggerMonitor triggerMonitor) {

        try {
            Trigger trigger = getTriggerFromScheduler(scheduler, triggerMonitor);

            boolean simpleTrigger = isSimpleTrigger(trigger);

            if (simpleTrigger) {
                return getSimpleJobDetail(scheduler, triggerMonitor);

            } else {
                return getCronJobDetail(scheduler, triggerMonitor);
            }


        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void start(Scheduler scheduler) {
        log.info("SCHEDULER - START COMMAND");
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void resume(Scheduler scheduler) {
        log.info("SCHEDULER - RESUME COMMAND");
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void pause(Scheduler scheduler) {
        log.info("SCHEDULER - PAUSE COMMAND");
        try {
            scheduler.standby();
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
            DailyDTO dailyDTO) {
        log.info("SCHEDULER - DAILY CONFIG COMMAND");

        try {
            Trigger newTrigger = newTrigger()
                    .withSchedule(dailyAtHourAndMinute(dailyDTO.getHour(), dailyDTO.getMinute())) // execute job daily at 11:30
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
            HourDTO hourDTO) {

        log.info("SCHEDULER - HOUR CONFIG COMMAND");

        Trigger newTrigger = newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(hourDTO.getHour())
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
            MinuteDTO minuteDTO) {

        log.info("SCHEDULER - MINUTE CONFIG COMMAND");
        Trigger newTrigger = newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(minuteDTO.getMinute())
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
            SecondDTO secondDTO) {

        log.info("SCHEDULER - SECOND CONFIG COMMAND");

        Trigger newTrigger = newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(secondDTO.getSecond())
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
