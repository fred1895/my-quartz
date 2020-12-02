package br.com.wod.quartz.core.usecases;

import br.com.wod.quartz.api.dto.time.DailyDTO;
import br.com.wod.quartz.api.dto.time.HourDTO;
import br.com.wod.quartz.api.dto.time.MinuteDTO;
import br.com.wod.quartz.api.dto.time.SecondDTO;
import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import br.com.wod.quartz.core.adapters.SchedulerMonitor;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import org.springframework.stereotype.Service;

@Service
public interface JobsConfigService {
    void dailyConfig(
            DailyDTO dailyDTO,
            SchedulerMonitor schedulerMonitor,
            JobDetailMonitor jobDetailMonitor,
            TriggerMonitor triggerMonitor
    );

    void hourConfig(
            HourDTO hourDTO,
            SchedulerMonitor schedulerMonitor,
            JobDetailMonitor jobDetailMonitor,
            TriggerMonitor triggerMonitor
    );

    void minuteConfig(
            MinuteDTO minuteDTO,
            SchedulerMonitor schedulerMonitor,
            JobDetailMonitor jobDetailMonitor,
            TriggerMonitor triggerMonitor
    );

    void secondConfig(
            SecondDTO secondDTO,
            SchedulerMonitor schedulerMonitor,
            JobDetailMonitor jobDetailMonitor,
            TriggerMonitor triggerMonitor
    );
}
