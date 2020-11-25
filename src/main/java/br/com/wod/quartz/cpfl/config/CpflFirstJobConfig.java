package br.com.wod.quartz.cpfl.config;

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

import java.io.IOException;
import java.util.Properties;

import static br.com.wod.quartz.config.BeanConstriants.*;
import static br.com.wod.quartz.config.SchedulerConfigUtil.createJobDetail;
import static br.com.wod.quartz.config.SchedulerConfigUtil.setInfo;

@Configuration
public class CpflFirstJobConfig {

    @Value("${cpfl.first-job.name}")
    private String jobName;

    @Value("${cpfl.first-job.group}")
    private String jobGroup;

    @Value("${cpfl.first-job.description}")
    private String jobDescription;

    @Bean(name = JOB_CPFL_FIRST)
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetail = createJobDetail(CpflFirstJob.class);
        jobDetail.setDurability(true);
        return setInfo(jobDetail, jobName, jobGroup, jobDescription);
    }

    @Bean(name = TRIGGER_MONITOR_CPFL_FIRST)
    public TriggerMonitor createTriggerMonitor() {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        return triggerMonitor;
    }

    @Bean(name = SCHED_CPFL_FIRST)
    public SchedulerFactoryBean schedulerFactoryBean(
            @Qualifier(value = QUARTZ_PROPERTIES) Properties properties,
            @Qualifier(value = JOB_CPFL_FIRST) JobDetail jobDetail,
            @Qualifier(value = "cpflFisrtJobSchedulerJobFactory") JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setJobDetails(jobDetail);
        factory.setQuartzProperties(properties);
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
