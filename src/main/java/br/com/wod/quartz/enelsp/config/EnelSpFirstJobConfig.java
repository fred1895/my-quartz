package br.com.wod.quartz.enelsp.config;

import br.com.wod.quartz.config.JobConfig;
import br.com.wod.quartz.enelsp.jobs.EnelSpFirstJob;
import br.com.wod.quartz.schedule.TriggerMonitor;
import br.com.wod.quartz.schedule.TriggerMonitorImpl;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import static br.com.wod.quartz.config.SchedulerConfigUtil.*;

@Configuration
public class EnelSpFirstJobConfig implements JobConfig {

    @Value("${enelsp.first-job.name}")
    private String jobName;

    @Value("${enelsp.first-job.group}")
    private String jobGroup;

    @Value("${enelsp.first-job.description}")
    private String jobDescription;

    @Override
    @Bean(name = "enelFirstJob")
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetail = createJobDetail(EnelSpFirstJob.class);
        return setInfo(jobDetail, jobName, jobGroup, jobDescription);
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
