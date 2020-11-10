package br.com.wod.quartz.cpfl.config;

import br.com.wod.quartz.config.JobConfig;
import br.com.wod.quartz.cpfl.jobs.CpflFirstJob;
import br.com.wod.quartz.schedule.TriggerMonitor;
import br.com.wod.quartz.schedule.TriggerMonitorImpl;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import static br.com.wod.quartz.config.SchedulerConfigUtil.createJobDetail;
import static br.com.wod.quartz.config.SchedulerConfigUtil.createTrigger;

@Configuration
public class CpflFirstJobConfig implements JobConfig {

    @Override
    @Bean(name = "cpflFirstJob")
    public JobDetailFactoryBean jobDetail() {
        return createJobDetail(CpflFirstJob.class);
    }

    @Override
    @Bean(name = "cpflFirstJobTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("cpflFirstJob") JobDetail jobDetail) {
        return createTrigger(jobDetail);
    }

    @Override
    @Bean(name = "cpflFirstJobTriggerMonitor")
    public TriggerMonitor createTriggerMonitor(@Qualifier("cpflFirstJobTrigger") Trigger trigger) {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        triggerMonitor.setTrigger(trigger);
        return triggerMonitor;
    }
}
