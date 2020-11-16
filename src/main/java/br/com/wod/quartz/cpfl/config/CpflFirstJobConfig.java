package br.com.wod.quartz.cpfl.config;

import br.com.wod.quartz.config.JobConfig;
import br.com.wod.quartz.cpfl.jobs.CpflFirstJob;
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
public class CpflFirstJobConfig implements JobConfig {

    @Value("${cpfl.first-job.name}")
    private String jobName;

    @Value("${cpfl.first-job.group}")
    private String jobGroup;

    @Value("${cpfl.first-job.description}")
    private String jobDescription;

    @Override
    @Bean(name = "cpflFirstJob")
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetail = createJobDetail(CpflFirstJob.class);
        return setInfo(jobDetail, jobName, jobGroup, jobDescription);
    }

    @Override
    @Bean(name = "cpflFirstJobTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("cpflFirstJob") JobDetail jobDetail) {
        return createTrigger(jobDetail);
    }

    @Override
    @Bean(name = "cpflFirstJobTriggerMonitor")
    public TriggerMonitor createTriggerMonitor(@Qualifier("cpflFirstJobTrigger") Trigger trigger) {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        triggerMonitor.setTrigger(trigger);
        return triggerMonitor;
    }

    @Bean(name = "cpflFisrtJobScheduler")
    public SchedulerFactoryBean schedulerFactoryBean(
            @Qualifier(value = "cpflFirstJobTrigger") Trigger trigger,
            @Qualifier(value = "cpflFisrtJobSchedulerJobFactory") JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setTriggers(trigger);
        factory.setJobFactory(jobFactory);
        factory.setAutoStartup(false);
        return factory;
    }

    @Bean(name = "cpflFisrtJobSchedulerJobFactory")
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

}
