package br.com.wod.quartz.enelsp.config;

import br.com.wod.quartz.config.JobConfig;
import br.com.wod.quartz.enelsp.jobs.EnelSpFirstJob;
import br.com.wod.quartz.schedule.AutowiringSpringBeanJobFactory;
import br.com.wod.quartz.schedule.TriggerMonitor;
import br.com.wod.quartz.schedule.TriggerMonitorImpl;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.io.IOException;
import java.util.Properties;

import static br.com.wod.quartz.config.SchedulerConfigUtil.*;

@Configuration
public class EnelSpFirstJobConfig  {

    @Value("${enelsp.first-job.name}")
    private String jobName;

    @Value("${enelsp.first-job.group}")
    private String jobGroup;

    @Value("${enelsp.first-job.description}")
    private String jobDescription;

    @Bean(name = "enelFirstJob")
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetail = createJobDetail(EnelSpFirstJob.class);
        jobDetail.setDurability(true);
        return setInfo(jobDetail, jobName, jobGroup, jobDescription);
    }

    @Bean(name = "enel_first_job_trigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("enelFirstJob") JobDetail jobDetail) {
        return createTrigger(jobDetail);
    }

    @Bean(name = "enelFirstJobTriggerMonitor")
    public TriggerMonitor createTriggerMonitor(@Qualifier("enel_first_job_trigger") Trigger trigger) {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        triggerMonitor.setTrigger(trigger);
        return triggerMonitor;
    }

    @Bean(name = "enelSpFirstJobScheduler")
    public SchedulerFactoryBean schedulerFactoryBean(
            @Qualifier(value = "enel_first_job_trigger") Trigger trigger,
            @Qualifier(value = "enelSpFirstJobSchedulerJobFactory") JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setTriggers(trigger);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        factory.setAutoStartup(false);
        return factory;
    }

    @Bean(name = "enelSpFirstJobSchedulerJobFactory")
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
