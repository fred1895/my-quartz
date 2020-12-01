package br.com.wod.quartz.core.usecases;

import br.com.wod.quartz.api.dto.time.DailyDTO;
import br.com.wod.quartz.api.dto.time.HourDTO;
import br.com.wod.quartz.api.dto.time.MinuteDTO;
import br.com.wod.quartz.api.dto.time.SecondDTO;
import br.com.wod.quartz.api.exception.MySchedulerException;
import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@Slf4j
public class JobsConfigServiceQrtz {

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    public void dailyConfig(
            DailyDTO dailyDTO,
            Scheduler scheduler,
            JobDetail jobDetail,
            TriggerMonitor triggerMonitor) {

        log.info("SCHEDULER - DAILY CONFIG COMMAND");

        try {
            Trigger newTrigger = newTrigger()
                    .withSchedule(dailyAtHourAndMinute(dailyDTO.getHour(), dailyDTO.getMinute())
                            .withMisfireHandlingInstructionDoNothing())
                    .forJob(jobDetail.getKey())
                    .build();

            Trigger trigger = triggerMonitor.getTrigger();
            if (trigger != null) {
                scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
            } else {
                scheduler.scheduleJob(newTrigger);
            }
            triggerMonitor.setTrigger(newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void hourConfig(
            HourDTO hourDTO,
            Scheduler scheduler,
            JobDetail jobDetail,
            TriggerMonitor triggerMonitor) {

        log.info("SCHEDULER - SECOND CONFIG COMMAND");

        Trigger newTrigger = newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(hourDTO.getHour())
                        .withMisfireHandlingInstructionNextWithRemainingCount()
                        .repeatForever())
                .forJob(jobDetail.getKey())
                .build();

        try {
            Trigger trigger = triggerMonitor.getTrigger();
            if (trigger != null) {
                scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
            } else {
                scheduler.scheduleJob(newTrigger);
            }
            triggerMonitor.setTrigger(newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void minuteConfig(
            MinuteDTO minuteDTO,
            Scheduler scheduler,
            JobDetail jobDetail,
            TriggerMonitor triggerMonitor) {

        log.info("SCHEDULER - SECOND CONFIG COMMAND");

        Trigger newTrigger = newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(minuteDTO.getMinute())
                        .withMisfireHandlingInstructionNextWithRemainingCount()
                        .repeatForever())
                .forJob(jobDetail.getKey())
                .build();

        try {
            Trigger trigger = triggerMonitor.getTrigger();
            if (trigger != null) {
                scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
            } else {
                scheduler.scheduleJob(newTrigger);
            }
            triggerMonitor.setTrigger(newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void secondConfig(
            SecondDTO secondDTO,
            Scheduler scheduler,
            JobDetail jobDetail,
            TriggerMonitor triggerMonitor) {

        log.info("SCHEDULER - SECOND CONFIG COMMAND");

        Trigger newTrigger = newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(secondDTO.getSecond())
                        .withMisfireHandlingInstructionNextWithRemainingCount()
                        .repeatForever())
                .forJob(jobDetail.getKey())
                .build();

        try {
            Trigger trigger = triggerMonitor.getTrigger();
            if (trigger != null) {
                scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
            } else {
                scheduler.scheduleJob(newTrigger);
            }
            triggerMonitor.setTrigger(newTrigger);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }
}
