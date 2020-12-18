package br.com.wod.quartz.api.facade;

import br.com.wod.quartz.api.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.api.dto.jobinfo.QrtzTriggersDTO;
import br.com.wod.quartz.api.dto.time.DailyDTO;
import br.com.wod.quartz.api.dto.time.HourDTO;
import br.com.wod.quartz.api.dto.time.MinuteDTO;
import br.com.wod.quartz.api.dto.time.SecondDTO;
import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import br.com.wod.quartz.core.adapters.SchedulerMonitor;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import br.com.wod.quartz.infrastructure.usecases.JobsConfigServiceQrtz;
import br.com.wod.quartz.infrastructure.usecases.JobsInfoServiceQrtz;
import br.com.wod.quartz.infrastructure.usecases.JobsPlayerServiceQrtz;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static br.com.wod.quartz.configurations.cpfl.second.CpflSecondBeans.*;

@Service
@Slf4j
public class CpflSecondJobFacade {

    @Autowired
    @Qualifier(SCHED_CPFL_SECOND)
    private Scheduler scheduler;

    @Autowired
    @Qualifier(JOB_CPFL_SECOND)
    private JobDetail jobDetail;

    @Autowired
    @Qualifier("job_monitor_second")
    private JobDetailMonitor jobDetailMonitor;

    @Autowired
    @Qualifier("scheduler_monitor_second")
    private SchedulerMonitor schedulerMonitor;

    @Autowired
    @Qualifier(TRIGGER_MONITOR_CPFL_SECOND)
    private TriggerMonitor triggerMonitor;

    @Autowired
    private JobsPlayerServiceQrtz playerService;

    @Autowired
    private JobsInfoServiceQrtz infoService;

    @Autowired
    private JobsConfigServiceQrtz configService;

    public void startJob() {
        playerService.start(scheduler, jobDetail, triggerMonitor);
    }

    public QrtzJobDetailsDTO getJobInfo() {
        return infoService.getJobInfo(scheduler, jobDetail);
    }

    public QrtzTriggersDTO getTriggerInfo() {
        return infoService.getTriggerInfo(scheduler, triggerMonitor);
    }

    public void resumeJob() {
        playerService.resume(scheduler);
    }

    public void pauseJob() {
        playerService.pause(scheduler);
    }

    public void deleteJob() {
        playerService.delete(scheduler, jobDetail);
    }

    public void dailyJobConfig(DailyDTO dailyDTO) {
        configService.dailyConfig(dailyDTO, schedulerMonitor, jobDetailMonitor, triggerMonitor);
    }

    public void hourJobConfig(HourDTO hourDTO) {
        configService.hourConfig(hourDTO, schedulerMonitor, jobDetailMonitor, triggerMonitor);
    }

    public void minuteJobConfig(MinuteDTO minuteDTO) {
        configService.minuteConfig(minuteDTO, schedulerMonitor, jobDetailMonitor, triggerMonitor);
    }

    public void secondJobConfig(SecondDTO secondDTO) {
        configService.secondConfig(secondDTO, schedulerMonitor, jobDetailMonitor, triggerMonitor);
    }

}
