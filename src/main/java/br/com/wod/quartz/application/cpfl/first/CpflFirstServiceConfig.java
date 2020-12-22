package br.com.wod.quartz.application.cpfl.first;

import br.com.wod.quartz.core.adapters.*;
import br.com.wod.quartz.infrastructure.usecases.JobsConfigService;
import br.com.wod.quartz.infrastructure.usecases.JobsInfoService;
import br.com.wod.quartz.infrastructure.usecases.JobsPlayerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static br.com.wod.quartz.application.cpfl.first.CpflFirstBeans.*;
import static br.com.wod.quartz.application.cpfl.first.CpflFirstBeans.TRIGGER_MONITOR_CPFL_FIRST;

@Configuration
public class CpflFirstServiceConfig {

    @Bean(name = CONFIG_CPFL_FIRST)
    public IJobsConfigService jobsConfigService(
            @Qualifier(value = SCHED_CPFL_FIRST_MONITOR) SchedulerMonitor schedulerMonitor,
            @Qualifier(value = JOB_CPFL_FIRST_MONITOR) JobDetailMonitor jobDetailMonitor,
            @Qualifier(value = TRIGGER_MONITOR_CPFL_FIRST) TriggerMonitor triggerMonitor
    ) {
        return new JobsConfigService(schedulerMonitor, jobDetailMonitor, triggerMonitor);
    }

    @Bean(name = INFO_CPFL_FIRST)
    public IJobsInfoService jobsInfoService(
            @Qualifier(value = SCHED_CPFL_FIRST_MONITOR) SchedulerMonitor schedulerMonitor,
            @Qualifier(value = JOB_CPFL_FIRST_MONITOR) JobDetailMonitor jobDetailMonitor,
            @Qualifier(value = TRIGGER_MONITOR_CPFL_FIRST) TriggerMonitor triggerMonitor
    ) {
        return new JobsInfoService(schedulerMonitor, jobDetailMonitor, triggerMonitor);
    }

    @Bean(name = PLAYER_CPFL_FIRST)
    public IJobsPlayerService jobsPlayerService(
            @Qualifier(value = SCHED_CPFL_FIRST_MONITOR) SchedulerMonitor schedulerMonitor,
            @Qualifier(value = JOB_CPFL_FIRST_MONITOR) JobDetailMonitor jobDetailMonitor,
            @Qualifier(value = TRIGGER_MONITOR_CPFL_FIRST) TriggerMonitor triggerMonitor
    ) {
        return new JobsPlayerService(schedulerMonitor, jobDetailMonitor, triggerMonitor);
    }

}
