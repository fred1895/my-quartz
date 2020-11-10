package br.com.wod.quartz.cpfl.config;

import br.com.wod.quartz.config.JobConfig;
import br.com.wod.quartz.cpfl.jobs.CpflSecondJob;
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
public class CpflSecondJobConfig implements JobConfig {

    @Override
    @Bean(name = "cpflSecondJob")
    public JobDetailFactoryBean jobDetail() {
        return createJobDetail(CpflSecondJob.class);
    }

    @Override
    @Bean(name = "cpflSecondJobTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(
            @Qualifier("cpflSecondJob") JobDetail jobDetail) {
        return createTrigger(jobDetail);
    }

    @Override
    @Bean(name = "cpflSecondTriggerMonitor")
    public TriggerMonitor createTriggerMonitor(
            @Qualifier("cpflSecondJobTrigger") Trigger trigger) {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        triggerMonitor.setTrigger(trigger);
        return triggerMonitor;
    }
}
