package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.JobInfoDTO;
import br.com.wod.quartz.resource.exception.MySchedulerException;
import br.com.wod.quartz.schedule.TriggerMonitor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JobsBasicService {

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    public JobInfoDTO getInfo(Scheduler scheduler, JobDetail jobDetail) {
        JobInfoDTO jobInfo = new JobInfoDTO();

        try {
            JobDetailImpl jobDetailImpl = (JobDetailImpl) scheduler
                    .getJobDetail(jobDetail.getKey());

            if (jobDetailImpl != null) {
                jobInfo.setJobName(jobDetailImpl.getName());
                jobInfo.setJobGroup(jobDetailImpl.getGroup());
                jobInfo.setJobDescription(jobDetailImpl.getDescription());
            } else {
                throw new MySchedulerException(" Inicie o job para pegar suas infos");
            }
            return jobInfo;

        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void start(
            Scheduler scheduler,
            JobDetail jobDetail,
            TriggerMonitor triggerMonitor) {
        log.info("SCHEDULER - START COMMAND");
        try {
            scheduler.start();
            scheduler.scheduleJob(jobDetail, triggerMonitor.getTrigger());
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void resume(Scheduler scheduler, JobDetail jobDetail) {
        JobKey jobKey = jobDetail.getKey();
        log.info("SCHEDULER - RESUME COMMAND");
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void pause(Scheduler scheduler, JobDetail jobDetail) {
        JobKey jobKey = jobDetail.getKey();
        log.info("SCHEDULER - PAUSE COMMAND");
        try {
            scheduler.pauseJob(jobKey);
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

}
