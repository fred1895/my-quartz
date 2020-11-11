package br.com.wod.quartz.cpfl.config;

import br.com.wod.quartz.config.JobConfig;
import br.com.wod.quartz.cpfl.jobs.CpflFirstJob;
import br.com.wod.quartz.cpfl.jobs.CpflSecondJob;
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
public class CpflSecondJobConfig implements JobConfig {

    @Value("${cpfl.second-job.name}")
    private String jobName;

    @Value("${cpfl.second-job.group}")
    private String jobGroup;

    @Value("${cpfl.second-job.description}")
    private String jobDescription;

    @Override
    @Bean(name = "cpflSecondJob")
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetail = createJobDetail(CpflFirstJob.class);
        return setInfo(jobDetail, jobName, jobGroup, jobDescription);
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
