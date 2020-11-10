package br.com.wod.quartz.config;

import br.com.wod.quartz.schedule.TriggerMonitor;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

public interface JobConfig {

    JobDetailFactoryBean jobDetail();

    SimpleTriggerFactoryBean sampleJobTrigger(JobDetail jobDetail);

    TriggerMonitor createTriggerMonitor(Trigger trigger);
}
