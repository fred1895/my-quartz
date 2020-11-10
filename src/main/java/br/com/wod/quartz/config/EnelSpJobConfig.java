package br.com.wod.quartz.config;

import br.com.wod.quartz.jobs.MyJob;
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
public class EnelSpJobConfig implements JobConfig {

    @Override
    @Bean(name = "jobDetail")
    public JobDetailFactoryBean jobDetail() {
        return createJobDetail(MyJob.class);
    }

    @Override
    @Bean(name = "jobTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("jobDetail") JobDetail jobDetail) {
        return createTrigger(jobDetail, 10000L, 5);
    }

    @Override
    @Bean(name = "triggerMonitor")
    public TriggerMonitor createTriggerMonitor(@Qualifier("jobTrigger") Trigger trigger) {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        triggerMonitor.setTrigger(trigger);
        return triggerMonitor;
    }
}
