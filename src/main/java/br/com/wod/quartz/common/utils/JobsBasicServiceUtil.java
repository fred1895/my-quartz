package br.com.wod.quartz.common.utils;

import br.com.wod.quartz.api.dto.enums.SchedulerStates;
import br.com.wod.quartz.api.dto.jobinfo.QrtzTriggersDTOCronDTO;
import br.com.wod.quartz.api.dto.jobinfo.QrtzTriggersDTOSimpleDTO;
import br.com.wod.quartz.api.exception.MySchedulerException;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
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

    public static QrtzTriggersDTOSimpleDTO getSimpleJobDetail(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor) throws SchedulerException {

        QrtzTriggersDTOSimpleDTO triggerInfo = new QrtzTriggersDTOSimpleDTO();

        SimpleTriggerImpl jobTrigger = (SimpleTriggerImpl) scheduler
                .getTrigger(triggerMonitor.getTrigger().getKey());


        if (jobTrigger != null && scheduler.isStarted()) {

            triggerInfo.setPreviousFireTime(jobTrigger.getPreviousFireTime());
            triggerInfo.setNextFireTime(jobTrigger.getNextFireTime());
            triggerInfo.setRepeatInterval(jobTrigger.getRepeatInterval());
            triggerInfo.setTimesTriggered(jobTrigger.getTimesTriggered());

        } else {
            throw new MySchedulerException("Start the job to get the information about it");
        }
        return triggerInfo;
    }

    public static QrtzTriggersDTOCronDTO getCronJobDetail(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor) throws SchedulerException {

        QrtzTriggersDTOCronDTO triggerInfo = new QrtzTriggersDTOCronDTO();

        CronTriggerImpl jobTrigger = (CronTriggerImpl) scheduler
                .getTrigger(triggerMonitor.getTrigger().getKey());

        if (jobTrigger != null && scheduler.isStarted()) {
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

    public static SchedulerStates getJobStatus(Scheduler scheduler) {
        try {
            if (scheduler.isShutdown() || !scheduler.isStarted())
                return SchedulerStates.WAITING;
            else if (scheduler.isStarted() && scheduler.isInStandbyMode())
                return SchedulerStates.PAUSED;
            else
                return SchedulerStates.RUNNING;
        } catch (SchedulerException e) {
            throw new MySchedulerException("Error trying to get the information about the job: " + e.getMessage());
        }
    }



}
