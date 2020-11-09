package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.SchedulerConfigParam;
import br.com.wod.quartz.schedule.TriggerMonitor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

@Service
@Slf4j
public class EnelSpScheduleService {
    private static final int MILLS_IN_A_DAY = 1000 * 60 * 60 * 24;
    private static final int SEC_IN_A_DAY = 60 * 60 * 24;

    @Resource
    private Scheduler scheduler;

    @Resource
    private TriggerMonitor triggerMonitor;

    /*
    public void startJob() throws SchedulerException {
        scheduler.start();
        scheduler.scheduleJob(enelJob, simpleSchedule);
    }
    */

    public void startJob() throws SchedulerException {
        log.info("SCHEDULER - START COMMAND");
        scheduler.start();
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

    private long fromMillsIntervalToTriggerPerDay(long repeatIntervalInMills) {
        return (int) Math.ceil(MILLS_IN_A_DAY / repeatIntervalInMills);
    }

    private int fromTriggerPerDayToMillsInterval(long triggerPerDay) {
        return (int) Math.ceil(Long.valueOf(MILLS_IN_A_DAY) / triggerPerDay); // with ceil the triggerPerDay is a max value
    }

}
