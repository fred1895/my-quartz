package br.com.wod.quartz.enelsp.config;

import br.com.wod.quartz.config.JobConfig;
import br.com.wod.quartz.enelsp.jobs.EnelSpFirstJob;
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
public class EnelSpFirstJobConfig implements JobConfig {

    @Override
    @Bean(name = "enelFirstJob")
    public JobDetailFactoryBean jobDetail() {
        return createJobDetail(EnelSpFirstJob.class);
    }

    @Override
    @Bean(name = "enelFirstJobTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("enelFirstJob") JobDetail jobDetail) {
        return createTrigger(jobDetail);
    }

    @Override
    @Bean(name = "enelFirstJobTriggerMonitor")
    public TriggerMonitor createTriggerMonitor(@Qualifier("enelFirstJobTrigger") Trigger trigger) {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        triggerMonitor.setTrigger(trigger);
        return triggerMonitor;
    }
}
