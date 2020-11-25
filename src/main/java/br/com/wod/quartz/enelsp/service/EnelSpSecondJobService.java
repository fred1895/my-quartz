package br.com.wod.quartz.enelsp.service;

import br.com.wod.quartz.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.dto.jobinfo.QrtzTriggersDTO;
import br.com.wod.quartz.dto.time.DailyDTO;
import br.com.wod.quartz.dto.time.HourDTO;
import br.com.wod.quartz.dto.time.MinuteDTO;
import br.com.wod.quartz.dto.time.SecondDTO;
import br.com.wod.quartz.entities.QrtzTriggers;
import br.com.wod.quartz.repositories.QrtzJobDetailsRepository;
import br.com.wod.quartz.repositories.QrtzTriggersRepository;
import br.com.wod.quartz.resource.exception.MySchedulerException;
import br.com.wod.quartz.schedule.TriggerMonitor;
import br.com.wod.quartz.service.JobsBasicService;
import br.com.wod.quartz.service.SchedulerBaseService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.wod.quartz.config.BeanConstriants.*;

@Service
@Slf4j
public class EnelSpSecondJobService implements SchedulerBaseService {

    @Autowired
    @Qualifier(SCHED_ENELSP_SECOND)
    private Scheduler scheduler;

    @Autowired
    private JobsBasicService basicService;

    @Autowired
    @Qualifier(JOB_ENELSP_SECOND)
    private JobDetail jobDetail;

    @Autowired
    @Qualifier(TRIGGER_MONITOR_ENELSP_SECOND)
    private TriggerMonitor triggerMonitor;

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    @Autowired
    private QrtzTriggersRepository triggersRepository;

    @Autowired
    private QrtzJobDetailsRepository qrtzJobDetailsRepository;

    @Override
    public void startJob() {
        QrtzTriggers trigger = triggersRepository.findBySchedName(SCHED_ENELSP_SECOND);
        JobKey key = jobDetail.getKey();
        if (trigger != null) {
            try {
                List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(key);
                Trigger trigger1 = triggersOfJob.get(0);
                triggerMonitor.setTrigger(trigger1);
                scheduler.start();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        } else {
            throw new MySchedulerException(errorMsg + "Config the the job to start it");
        }
    }

    @Override
    public QrtzJobDetailsDTO getJobInfo() {
        return basicService.getJobInfo(scheduler, jobDetail);
    }

    @Override
    public QrtzTriggersDTO getTriggerInfo() {
        return basicService.getTriggerInfo(scheduler, triggerMonitor);
    }

    @Override
    public void resumeJob() {
        basicService.resume(scheduler);
    }

    @Override
    public void pauseJob() {
        basicService.pause(scheduler);
    }

    @Override
    public void deleteJob() {
        basicService.delete(scheduler, jobDetail);
    }

    @Override
    public void dailyJobConfig(DailyDTO dailyDTO) {
        basicService.dailyConfig(scheduler, triggerMonitor, dailyDTO, jobDetail);
    }

    @Override
    public void hourJobConfig(HourDTO hourDTO) {
        basicService.hourConfig(scheduler, triggerMonitor, hourDTO, jobDetail);
    }

    @Override
    public void minuteJobConfig(MinuteDTO minuteDTO) {
        basicService.minuteConfig(scheduler, triggerMonitor, minuteDTO, jobDetail);
    }

    @Override
    public void secondJobConfig(SecondDTO secondDTO) {
        basicService.secondConfig(scheduler, triggerMonitor, secondDTO, jobDetail);
    }

}
