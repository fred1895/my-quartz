package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.jobinfo.JobInfoCronDTO;
import br.com.wod.quartz.dto.jobinfo.JobInfoSimpleDTO;
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

    public static JobInfoSimpleDTO getSimpleJobDetail(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor,
            JobDetailImpl jobDetailImpl) throws SchedulerException {

        JobInfoSimpleDTO jobInfoDTO = new JobInfoSimpleDTO();

        SimpleTriggerImpl jobTrigger = (SimpleTriggerImpl) scheduler
                .getTrigger(triggerMonitor.getTrigger().getKey());


        if (jobDetailImpl != null && jobTrigger != null) {

            jobInfoDTO.setJobName(jobDetailImpl.getName());
            jobInfoDTO.setJobGroup(jobDetailImpl.getGroup());
            jobInfoDTO.setJobDescription(jobDetailImpl.getDescription());


            jobInfoDTO.setPreviousFireTime(jobTrigger.getPreviousFireTime());
            jobInfoDTO.setNextFireTime(jobTrigger.getNextFireTime());
            jobInfoDTO.setRepeatInterval(jobTrigger.getRepeatInterval());
            jobInfoDTO.setTimesTriggered(jobTrigger.getTimesTriggered());

        } else {
            throw new MySchedulerException("Start the job to get the information about it");
        }
        return jobInfoDTO;
    }

    public static JobInfoCronDTO getCronJobDetail(
            Scheduler scheduler,
            TriggerMonitor triggerMonitor,
            JobDetailImpl jobDetailImpl) throws SchedulerException {

        JobInfoCronDTO jobInfo = new JobInfoCronDTO();

        CronTriggerImpl jobTrigger = (CronTriggerImpl) scheduler
                .getTrigger(triggerMonitor.getTrigger().getKey());

        if (jobDetailImpl != null && jobTrigger != null) {
            jobInfo.setJobName(jobDetailImpl.getName());
            jobInfo.setJobGroup(jobDetailImpl.getGroup());
            jobInfo.setJobDescription(jobDetailImpl.getDescription());

            jobInfo.setPreviousFireTime(jobTrigger.getPreviousFireTime());
            jobInfo.setNextFireTime(jobTrigger.getNextFireTime());
        } else {
            throw new MySchedulerException("Start the job to get the information about it");
        }
        return jobInfo;
    }


    public static boolean isSimpleTrigger(Trigger trigger) {
        return (trigger instanceof SimpleTriggerImpl) ? true : false;
    }


}
