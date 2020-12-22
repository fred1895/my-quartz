package br.com.wod.quartz.infrastructure.usecases;

import br.com.wod.quartz.api.exception.MySchedulerException;
import br.com.wod.quartz.core.adapters.IJobsPlayerService;
import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import br.com.wod.quartz.core.adapters.SchedulerMonitor;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import br.com.wod.quartz.core.entities.QrtzTriggers;
import br.com.wod.quartz.infrastructure.repositories.JobsDetailsRepository;
import br.com.wod.quartz.infrastructure.repositories.TriggersRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Slf4j
public class JobsPlayerService implements IJobsPlayerService {

    private SchedulerMonitor schedulerMonitor;
    private JobDetailMonitor jobDetailMonitor;
    private TriggerMonitor triggerMonitor;

    public JobsPlayerService(SchedulerMonitor schedulerMonitor, JobDetailMonitor jobDetailMonitor, TriggerMonitor triggerMonitor) {
        this.schedulerMonitor = schedulerMonitor;
        this.jobDetailMonitor = jobDetailMonitor;
    }

    @Autowired
    private JobsDetailsRepository jobsDetailsRepository;

    @Autowired
    private TriggersRepository triggersRepository;

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    @Override
    public void start() {
        QrtzTriggers trigger = null;
        try {
            trigger = triggersRepository.findBySchedName(schedulerMonitor.getScheduler().getSchedulerName());
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }

        JobKey key = jobDetailMonitor.getJob().getKey();

        if (trigger != null) {
            try {
                List<? extends Trigger> triggersOfJob = schedulerMonitor.getScheduler().getTriggersOfJob(key);
                Trigger trigger1 = triggersOfJob.get(0);
                triggerMonitor.setTrigger(trigger1);
                schedulerMonitor.getScheduler().start();
            } catch (SchedulerException e) {
                throw new MySchedulerException(errorMsg + e.getMessage());
            }
        } else {
            throw new MySchedulerException(errorMsg + "Config the the job to start it");
        }
    }

    @Override
    public void resume() {
        log.info("SCHEDULER - RESUME COMMAND");
        try {
            schedulerMonitor.getScheduler().start();
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    @Override
    public void pause() {
        log.info("SCHEDULER - PAUSE COMMAND");
        try {
            schedulerMonitor.getScheduler().standby();
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    @Override
    public void delete() {
        JobKey jobKey = jobDetailMonitor.getJob().getKey();
        log.info("SCHEDULER - DELETE COMMAND");
        try {
            schedulerMonitor.getScheduler().deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

}
