package br.com.wod.quartz.config;

import br.com.wod.quartz.jobs.SecondJob;
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
public class SecondJobConfig implements JobConfig {

    @Override
    @Bean(name = "secondJobDetail")
    public JobDetailFactoryBean jobDetail() {
        return createJobDetail(SecondJob.class);
    }

    @Override
    @Bean(name = "secondJobTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(
            @Qualifier("secondJobDetail") JobDetail jobDetail) {
        return createTrigger(jobDetail, 20000L, 7);
    }

    @Override
    @Bean(name = "secondTriggerMonitor")
    public TriggerMonitor createTriggerMonitor(
            @Qualifier("secondJobTrigger") Trigger trigger) {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        triggerMonitor.setTrigger(trigger);
        return triggerMonitor;
    }
}