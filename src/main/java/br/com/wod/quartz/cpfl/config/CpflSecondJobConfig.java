package br.com.wod.quartz.cpfl.config;

import br.com.wod.quartz.config.JobConfig;
import br.com.wod.quartz.cpfl.jobs.CpflSecondJob;
import br.com.wod.quartz.schedule.AutowiringSpringBeanJobFactory;
import br.com.wod.quartz.schedule.TriggerMonitor;
import br.com.wod.quartz.schedule.TriggerMonitorImpl;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.io.IOException;

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
        JobDetailFactoryBean jobDetail = createJobDetail(CpflSecondJob.class);
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

    @Bean(name = "cpflSecondJobScheduler")
    public SchedulerFactoryBean schedulerFactoryBean(
            @Qualifier(value = "cpflSecondJobTrigger") Trigger trigger,
            @Qualifier(value = "enelSpFirstJobSchedulerJobFactory") JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setTriggers(trigger);
        factory.setJobFactory(jobFactory);
        factory.setAutoStartup(false);
        return factory;
    }

    @Bean(name = "cpflSecondJobSchedulerJobFactory")
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

}
