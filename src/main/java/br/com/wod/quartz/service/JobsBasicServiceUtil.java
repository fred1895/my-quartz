package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.JobInfoDTO;
import br.com.wod.quartz.resource.exception.MySchedulerException;
import br.com.wod.quartz.schedule.TriggerMonitor;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;

public class JobsBasicServiceUtil {

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

    public static JobInfoDTO getSimpleJobDetail(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor,
            JobDetailImpl jobDetailImpl,
            JobInfoDTO jobInfo) throws SchedulerException {

        SimpleTriggerImpl jobTrigger = (SimpleTriggerImpl) scheduler
                .getTrigger(triggerMonitor.getTrigger().getKey());

        if (jobDetailImpl != null && jobTrigger != null) {
            jobInfo.setJobName(jobDetailImpl.getName());
            jobInfo.setJobGroup(jobDetailImpl.getGroup());
            jobInfo.setJobDescription(jobDetailImpl.getDescription());

            jobInfo.setPreviousFireTime(jobTrigger.getPreviousFireTime());
            jobInfo.setNextFireTime(jobTrigger.getNextFireTime());
            jobInfo.setRepeatInterval(jobTrigger.getRepeatInterval());
            jobInfo.setTimesTriggered(jobTrigger.getTimesTriggered());
        } else {
            throw new MySchedulerException("Inicie o job para pegar suas infos");
        }
        return jobInfo;
    }

    public static JobInfoDTO getCronJobDetail(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor,
            JobDetailImpl jobDetailImpl,
            JobInfoDTO jobInfo) throws SchedulerException {

        CronTriggerImpl jobTrigger = (CronTriggerImpl) scheduler
                .getTrigger(triggerMonitor.getTrigger().getKey());

        if (jobDetailImpl != null && jobTrigger != null) {
            jobInfo.setJobName(jobDetailImpl.getName());
            jobInfo.setJobGroup(jobDetailImpl.getGroup());
            jobInfo.setJobDescription(jobDetailImpl.getDescription());

            jobInfo.setPreviousFireTime(jobTrigger.getPreviousFireTime());
            jobInfo.setNextFireTime(jobTrigger.getNextFireTime());
        } else {
            throw new MySchedulerException("Inicie o job para pegar suas infos");
        }
        return jobInfo;
    }


    public static boolean isSimpleTrigger(Trigger trigger) {
        return (trigger instanceof SimpleTriggerImpl) ? true : false;
    }
}
