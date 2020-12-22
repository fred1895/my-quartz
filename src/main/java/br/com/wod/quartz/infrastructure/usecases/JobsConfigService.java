package br.com.wod.quartz.infrastructure.usecases;

import br.com.wod.quartz.api.dto.time.DailyDTO;
import br.com.wod.quartz.api.dto.time.HourDTO;
import br.com.wod.quartz.api.dto.time.MinuteDTO;
import br.com.wod.quartz.api.dto.time.SecondDTO;
import br.com.wod.quartz.api.exception.MySchedulerException;
import br.com.wod.quartz.core.adapters.IJobsConfigService;
import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import br.com.wod.quartz.core.adapters.SchedulerMonitor;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Value;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.TriggerBuilder.newTrigger;

@Slf4j
public class JobsConfigService implements IJobsConfigService {

    private SchedulerMonitor schedulerMonitor;
    private JobDetailMonitor jobDetailMonitor;
    private TriggerMonitor triggerMonitor;

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    public JobsConfigService(SchedulerMonitor schedulerMonitor, JobDetailMonitor jobDetailMonitor, TriggerMonitor triggerMonitor) {
        this.schedulerMonitor = schedulerMonitor;
        this.jobDetailMonitor = jobDetailMonitor;
        this.triggerMonitor = triggerMonitor;
    }

    @Override
    public void dailyConfig(DailyDTO dailyDTO) {
        try {
            Trigger newTrigger = newTrigger()
                    .withSchedule(dailyAtHourAndMinute(dailyDTO.getHour(), dailyDTO.getMinute())
                            .withMisfireHandlingInstructionDoNothing())
                    .forJob(jobDetailMonitor.getJob().getKey())
                    .build();

            Trigger trigger = triggerMonitor.getTrigger();
            if (trigger != null) {
                schedulerMonitor.getScheduler().rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
            } else {
                schedulerMonitor.getScheduler().scheduleJob(newTrigger);
            }
            triggerMonitor.setTrigger(newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    @Override
    public void hourConfig(HourDTO hourDTO) {
        Trigger newTrigger = newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(hourDTO.getHour())
                        .withMisfireHandlingInstructionNextWithRemainingCount()
                        .repeatForever())
                .forJob(jobDetailMonitor.getJob().getKey())
                .build();

        try {
            Trigger trigger = triggerMonitor.getTrigger();
            if (trigger != null) {
                schedulerMonitor.getScheduler().rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
            } else {
                schedulerMonitor.getScheduler().scheduleJob(newTrigger);
            }
            triggerMonitor.setTrigger(newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    @Override
    public void minuteConfig(MinuteDTO minuteDTO) {
        log.info("SCHEDULER - SECOND CONFIG COMMAND");

        Trigger newTrigger = newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(minuteDTO.getMinute())
                        .withMisfireHandlingInstructionNextWithRemainingCount()
                        .repeatForever())
                .forJob(jobDetailMonitor.getJob().getKey())
                .build();

        try {
            Trigger trigger = triggerMonitor.getTrigger();
            if (trigger != null) {
                schedulerMonitor.getScheduler().rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
            } else {
                schedulerMonitor.getScheduler().scheduleJob(newTrigger);
            }
            triggerMonitor.setTrigger(newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    @Override
    public void secondConfig(SecondDTO secondDTO) {
        log.info("SCHEDULER - SECOND CONFIG COMMAND");

        Trigger newTrigger = newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(secondDTO.getSecond())
                        .withMisfireHandlingInstructionNextWithRemainingCount()
                        .repeatForever())
                .forJob(jobDetailMonitor.getJob().getKey())
                .build();

        try {
            Trigger trigger = triggerMonitor.getTrigger();
            if (trigger != null) {
                schedulerMonitor.getScheduler().rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
            } else {
                schedulerMonitor.getScheduler().scheduleJob(newTrigger);
            }
            triggerMonitor.setTrigger(newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }
}
