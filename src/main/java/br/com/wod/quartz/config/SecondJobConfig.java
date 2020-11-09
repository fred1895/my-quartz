package br.com.wod.quartz.config;

import br.com.wod.quartz.jobs.SecondJob;
import br.com.wod.quartz.schedule.TriggerMonitor;
import br.com.wod.quartz.schedule.TriggerMonitorImpl;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import javax.annotation.Resource;

import static br.com.wod.quartz.config.SchedulerConfig.createJobDetail;
import static br.com.wod.quartz.config.SchedulerConfig.createTrigger;

@Configuration
public class SecondJobConfig {

    @Resource
    private Scheduler scheduler;

    @Bean(name = "secondJobDetail")
    public JobDetailFactoryBean secondJobDetail() {
        return createJobDetail(SecondJob.class);
    }

    @Bean(name = "secondJobTrigger")
    public SimpleTriggerFactoryBean secondJobTrigger(
            @Qualifier("secondJobDetail") JobDetail jobDetail
    ) {
        return createTrigger(jobDetail, 10000L, 5);
    }

    @Bean(name = "secondTriggerMonitor")
    public TriggerMonitor secondTriggerMonitor(@Qualifier("secondJobTrigger") Trigger trigger) {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        triggerMonitor.setTrigger(trigger);
        return triggerMonitor;
    }


}
