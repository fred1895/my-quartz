package br.com.wod.quartz.application.cpfl.second;

import br.com.wod.quartz.core.adapters.*;
import br.com.wod.quartz.infrastructure.usecases.JobsConfigService;
import br.com.wod.quartz.infrastructure.usecases.JobsInfoService;
import br.com.wod.quartz.infrastructure.usecases.JobsPlayerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static br.com.wod.quartz.application.cpfl.second.CpflSecondBeans.*;

@Configuration
public class CpflSecondServiceConfig {

    @Bean(name = CONFIG_CPFL_SECOND)
    public IJobsConfigService jobsConfigService(
            @Qualifier(value = SCHED_CPFL_SECOND_MONITOR) SchedulerMonitor schedulerMonitor,
            @Qualifier(value = JOB_CPFL_SECOND_MONITOR) JobDetailMonitor jobDetailMonitor,
            @Qualifier(value = TRIGGER_MONITOR_CPFL_SECOND) TriggerMonitor triggerMonitor
    ) {
        return new JobsConfigService(schedulerMonitor, jobDetailMonitor, triggerMonitor);
    }

    @Bean(name = INFO_CPFL_SECOND)
    public IJobsInfoService jobsInfoService(
            @Qualifier(value = SCHED_CPFL_SECOND_MONITOR) SchedulerMonitor schedulerMonitor,
            @Qualifier(value = JOB_CPFL_SECOND_MONITOR) JobDetailMonitor jobDetailMonitor,
            @Qualifier(value = TRIGGER_MONITOR_CPFL_SECOND) TriggerMonitor triggerMonitor
    ) {
        return new JobsInfoService(schedulerMonitor, jobDetailMonitor, triggerMonitor);
    }

    @Bean(name = PLAYER_CPFL_SECOND)
    public IJobsPlayerService jobsPlayerService(
            @Qualifier(value = SCHED_CPFL_SECOND_MONITOR) SchedulerMonitor schedulerMonitor,
            @Qualifier(value = JOB_CPFL_SECOND_MONITOR) JobDetailMonitor jobDetailMonitor,
            @Qualifier(value = TRIGGER_MONITOR_CPFL_SECOND) TriggerMonitor triggerMonitor
    ) {
        return new JobsPlayerService(schedulerMonitor, jobDetailMonitor, triggerMonitor);
    }
}
