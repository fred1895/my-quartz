package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.SchedulerConfigParam;
import br.com.wod.quartz.dto.TimeDTO;
import br.com.wod.quartz.schedule.TriggerMonitor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class EnelSpScheduleService {
    private static final int MILLS_IN_A_DAY = 1000 * 60 * 60 * 24;
    private static final int SEC_IN_A_DAY = 60 * 60 * 24;

    @Resource
    private Scheduler scheduler;

    @Resource
    private JobDetail jobDetail;

    @Resource
    private TriggerMonitor triggerMonitor;

    public void startJob() throws SchedulerException {
        log.info("SCHEDULER - START COMMAND");
        scheduler.start();
        scheduler.scheduleJob(jobDetail, triggerMonitor.getTrigger());
    }

    public SchedulerConfigParam postConfig(SchedulerConfigParam config) throws SchedulerException {
        log.info("SCHEDULER - NEW CONFIG {}", config);
        SimpleTrigger trigger = (SimpleTrigger) triggerMonitor.getTrigger();

        TriggerBuilder<SimpleTrigger> triggerBuilder = trigger.getTriggerBuilder();

        int intervalInMills = fromTriggerPerDayToMillsInterval(config.getTriggerPerDay());
        Trigger newTrigger = triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(intervalInMills).withRepeatCount(config.getMaxCount() - 1)).build();

        scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
        triggerMonitor.setTrigger(newTrigger);
        return config;
    }

    public void myJobTaskConfig() throws SchedulerException {
        SimpleTrigger trigger = (SimpleTrigger) triggerMonitor.getTrigger();

        TriggerBuilder<SimpleTrigger> triggerBuilder = trigger.getTriggerBuilder();

        Trigger newTrigger = triggerBuilder
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withRepeatCount(5)
                        .withIntervalInSeconds(3))
                .build();

        scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
        triggerMonitor.setTrigger(newTrigger);
    }

    public void dailyHourByHourJobConfig(TimeDTO timeDTO) throws SchedulerException {
        SimpleTrigger trigger = (SimpleTrigger) triggerMonitor.getTrigger();

        TriggerBuilder<SimpleTrigger> triggerBuilder = trigger.getTriggerBuilder();

        Trigger newTrigger = triggerBuilder
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(timeDTO.getHour())
                        .repeatForever())
                .build();

        scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
        triggerMonitor.setTrigger(newTrigger);

    }

    public void dailyJobConfig(TimeDTO timeDTO) throws SchedulerException {
        SimpleTrigger trigger = (SimpleTrigger) triggerMonitor.getTrigger();

        Trigger newTrigger = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(timeDTO.getHour(), timeDTO.getMinute()))
                .build();

        scheduler.rescheduleJob(triggerMonitor.getTrigger().getKey(), newTrigger);
        triggerMonitor.setTrigger(newTrigger);

    }

    private long fromMillsIntervalToTriggerPerDay(long repeatIntervalInMills) {
        return (int) Math.ceil(MILLS_IN_A_DAY / repeatIntervalInMills);
    }

    private int fromTriggerPerDayToMillsInterval(long triggerPerDay) {
        return (int) Math.ceil(Long.valueOf(MILLS_IN_A_DAY) / triggerPerDay); // with ceil the triggerPerDay is a max value
    }

}
