package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.SchedulerStates.SchedulerStates;
import br.com.wod.quartz.dto.jobinfo.TriggerInfoCronDTO;
import br.com.wod.quartz.dto.jobinfo.TriggerInfoSimpleDTO;
import br.com.wod.quartz.resource.exception.MySchedulerException;
import br.com.wod.quartz.schedule.TriggerMonitor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Value;

public class JobsBasicServiceUtil {

    @Value("${error.job-info.msg}")
    private String jobInfoError;

    public static Trigger getTriggerFromScheduler(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor) throws SchedulerException {

        return scheduler
                .getTrigger(triggerMonitor
                        .getTrigger()
                        .getTriggerBuilder()
                        .build()
                        .getKey());

    }

    public static TriggerInfoSimpleDTO getSimpleJobDetail(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor) throws SchedulerException {

        TriggerInfoSimpleDTO triggerInfo = new TriggerInfoSimpleDTO();

        SimpleTriggerImpl jobTrigger = (SimpleTriggerImpl) scheduler
                .getTrigger(triggerMonitor.getTrigger().getKey());


        if (jobTrigger != null) {

            triggerInfo.setPreviousFireTime(jobTrigger.getPreviousFireTime());
            triggerInfo.setNextFireTime(jobTrigger.getNextFireTime());
            triggerInfo.setRepeatInterval(jobTrigger.getRepeatInterval());
            triggerInfo.setTimesTriggered(jobTrigger.getTimesTriggered());

        } else {
            throw new MySchedulerException("Start the job to get the information about it");
        }
        return triggerInfo;
    }

    public static TriggerInfoCronDTO getCronJobDetail(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor) throws SchedulerException {

        TriggerInfoCronDTO triggerInfo = new TriggerInfoCronDTO();

        CronTriggerImpl jobTrigger = (CronTriggerImpl) scheduler
                .getTrigger(triggerMonitor.getTrigger().getKey());

        if (jobTrigger != null) {
            triggerInfo.setPreviousFireTime(jobTrigger.getPreviousFireTime());
            triggerInfo.setNextFireTime(jobTrigger.getNextFireTime());
        } else {
            throw new MySchedulerException("Start the job to get the information about it");
        }
        return triggerInfo;
    }


    public static boolean isSimpleTrigger(Trigger trigger) {
        return (trigger instanceof SimpleTriggerImpl) ? true : false;
    }

    public static String getJobStatus(Scheduler scheduler) {
        try {
            if (scheduler.isShutdown() || !scheduler.isStarted())
                return SchedulerStates.STOPPED.toString();
            else if (scheduler.isStarted() && scheduler.isInStandbyMode())
                return SchedulerStates.PAUSED.toString();
            else
                return SchedulerStates.RUNNING.toString();
        } catch (SchedulerException e) {
            throw new MySchedulerException("Error trying to get the information abut the job: " + e.getMessage());
        }
    }



}
