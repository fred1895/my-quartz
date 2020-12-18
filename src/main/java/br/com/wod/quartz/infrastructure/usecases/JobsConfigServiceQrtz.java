package br.com.wod.quartz.infrastructure.usecases;

import br.com.wod.quartz.api.dto.time.DailyDTO;
import br.com.wod.quartz.api.dto.time.HourDTO;
import br.com.wod.quartz.api.dto.time.MinuteDTO;
import br.com.wod.quartz.api.dto.time.SecondDTO;
import br.com.wod.quartz.api.exception.MySchedulerException;
import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import br.com.wod.quartz.core.adapters.JobsConfigService;
import br.com.wod.quartz.core.adapters.SchedulerMonitor;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@Slf4j
public class JobsConfigServiceQrtz implements JobsConfigService {

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    @Override
    public void dailyConfig(
            DailyDTO dailyDTO,
            SchedulerMonitor schedulerMonitor,
            JobDetailMonitor jobDetailMonitor,
            TriggerMonitor triggerMonitor) {

        log.info("SCHEDULER - DAILY CONFIG COMMAND");

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
    public void hourConfig(
            HourDTO hourDTO,
            SchedulerMonitor schedulerMonitor,
            JobDetailMonitor jobDetailMonitor,
            TriggerMonitor triggerMonitor) {

        log.info("SCHEDULER - SECOND CONFIG COMMAND");

        Trigger newTrigger = newTrigger()
                .startNow()
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
    public void minuteConfig(
            MinuteDTO minuteDTO,
            SchedulerMonitor schedulerMonitor,
            JobDetailMonitor jobDetailMonitor,
            TriggerMonitor triggerMonitor) {

        log.info("SCHEDULER - SECOND CONFIG COMMAND");

        Trigger newTrigger = newTrigger()
                .startNow()
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
    public void secondConfig(
            SecondDTO secondDTO,
            SchedulerMonitor schedulerMonitor,
            JobDetailMonitor jobDetailMonitor,
            TriggerMonitor triggerMonitor) {

        log.info("SCHEDULER - SECOND CONFIG COMMAND");

        Trigger newTrigger = newTrigger()
                .startNow()
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
