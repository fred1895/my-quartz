package br.com.wod.quartz.infrastructure.usecases;

import br.com.wod.quartz.core.entities.QrtzTriggers;
import br.com.wod.quartz.infrastructure.repositories.JobsDetailsRepository;
import br.com.wod.quartz.infrastructure.repositories.TriggersRepository;
import br.com.wod.quartz.api.exception.MySchedulerException;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class JobsPlayerServiceQrtz {

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    @Autowired
    private TriggersRepository triggersRepository;

    @Autowired
    private JobsDetailsRepository jobsDetailsRepository;

    public void start(Scheduler scheduler, JobDetail jobDetail, TriggerMonitor triggerMonitor) {
        QrtzTriggers trigger = null;
        try {
            trigger = triggersRepository.findBySchedName(scheduler.getSchedulerName());
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }

        JobKey key = jobDetail.getKey();

        if (trigger != null) {
            try {
                List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(key);
                Trigger trigger1 = triggersOfJob.get(0);
                triggerMonitor.setTrigger(trigger1);
                scheduler.start();
            } catch (SchedulerException e) {
                throw new MySchedulerException(errorMsg + e.getMessage());
            }
        } else {
            throw new MySchedulerException(errorMsg + "Config the the job to start it");
        }
    }

    public void resume(Scheduler scheduler) {
        log.info("SCHEDULER - RESUME COMMAND");
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void pause(Scheduler scheduler) {
        log.info("SCHEDULER - PAUSE COMMAND");
        try {
            scheduler.standby();
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
