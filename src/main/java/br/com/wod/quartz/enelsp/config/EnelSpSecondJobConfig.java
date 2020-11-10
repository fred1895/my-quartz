package br.com.wod.quartz.enelsp.config;

import br.com.wod.quartz.config.JobConfig;
import br.com.wod.quartz.enelsp.jobs.EnelSpSecondJob;
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
public class EnelSpSecondJobConfig implements JobConfig {

    @Override
    @Bean(name = "enelSecondJob")
    public JobDetailFactoryBean jobDetail() {
        return createJobDetail(EnelSpSecondJob.class);
    }

    @Override
    @Bean(name = "enelSecondJobTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(
            @Qualifier("enelSecondJob") JobDetail jobDetail) {
        return createTrigger(jobDetail);
    }

    @Override
    @Bean(name = "enelSecondTriggerMonitor")
    public TriggerMonitor createTriggerMonitor(
            @Qualifier("enelSecondJobTrigger") Trigger trigger) {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        triggerMonitor.setTrigger(trigger);
        return triggerMonitor;
    }
}
