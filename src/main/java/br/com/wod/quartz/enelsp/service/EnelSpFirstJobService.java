package br.com.wod.quartz.enelsp.service;

import br.com.wod.quartz.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.dto.jobinfo.QrtzTriggersDTO;
import br.com.wod.quartz.dto.time.DailyDTO;
import br.com.wod.quartz.dto.time.HourDTO;
import br.com.wod.quartz.dto.time.MinuteDTO;
import br.com.wod.quartz.dto.time.SecondDTO;
import br.com.wod.quartz.repositories.QrtzJobDetailsRepository;
import br.com.wod.quartz.repositories.QrtzTriggersRepository;
import br.com.wod.quartz.schedule.TriggerMonitor;
import br.com.wod.quartz.service.JobsBasicService;
import br.com.wod.quartz.service.SchedulerBaseService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EnelSpFirstJobService implements SchedulerBaseService {

    @Autowired
    @Qualifier("enelSpFirstJobScheduler")
    private Scheduler scheduler;

    @Autowired
    private JobsBasicService basicService;

    @Autowired
    @Qualifier("enelFirstJob")
    private JobDetail jobDetail;

    @Autowired
    @Qualifier("enelFirstJobTriggerMonitor")
    private TriggerMonitor triggerMonitor;

    @Autowired
    private QrtzTriggersRepository triggersRepository;

    @Autowired
    private QrtzJobDetailsRepository qrtzJobDetailsRepository;

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    @Override
    public QrtzJobDetailsDTO getJobInfo() {
        return basicService.getJobInfo(scheduler, jobDetail);
    }

    @Override
    public QrtzTriggersDTO getTriggerInfo() {
        return basicService.getTriggerInfo(scheduler, triggerMonitor);
    }

    @Override
    public void startJob() {
        basicService.start(scheduler);
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
        basicService.dailyConfig(scheduler, triggerMonitor, dailyDTO);
    }

    @Override
    public void hourJobConfig(HourDTO hourDTO) {
        basicService.hourConfig(scheduler, triggerMonitor, hourDTO);
    }

    @Override
    public void minuteJobConfig(MinuteDTO minuteDTO) {
        basicService.minuteConfig(scheduler, triggerMonitor, minuteDTO);
    }

    @Override
    public void secondJobConfig(SecondDTO secondDTO) {
        basicService.secondConfig(scheduler, triggerMonitor, secondDTO);
    }

    public void secondJobConfig2(SecondDTO secondDTO) {
        basicService.secondConfig2(scheduler, triggerMonitor, secondDTO, jobDetail);
    }

}
