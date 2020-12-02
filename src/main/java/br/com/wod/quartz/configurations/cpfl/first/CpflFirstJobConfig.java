package br.com.wod.quartz.configurations.cpfl.first;

import br.com.wod.quartz.common.schedule.AutowiringSpringBeanJobFactory;
import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import br.com.wod.quartz.core.adapters.SchedulerMonitor;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import br.com.wod.quartz.infrastructure.providers.JobDetailMonitorImpl;
import br.com.wod.quartz.infrastructure.providers.SchedulerMonitorImpl;
import br.com.wod.quartz.infrastructure.providers.TriggerMonitorImpl;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
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

import static br.com.wod.quartz.configurations.cpfl.first.CpflFirstBeans.*;
import static br.com.wod.quartz.configurations.utils.BeanConstriants.QUARTZ_PROPERTIES;
import static br.com.wod.quartz.configurations.utils.SchedulerConfigUtil.createJobDetail;
import static br.com.wod.quartz.configurations.utils.SchedulerConfigUtil.setInfo;

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

    @Bean(name = "job_monitor")
    public JobDetailMonitor createJobMonitor(
            @Qualifier(JOB_CPFL_FIRST) JobDetail jobDetail
    ) {
        JobDetailMonitor jobDetailMonitor = new JobDetailMonitorImpl();
        jobDetailMonitor.setJob(jobDetail);
        return jobDetailMonitor;
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

    @Bean(name = "scheduler_monitor")
    public SchedulerMonitor schedulerMonitor(
            @Qualifier(value = SCHED_CPFL_FIRST) Scheduler scheduler
    ) {
        SchedulerMonitor schedulerMonitor = new SchedulerMonitorImpl();
        schedulerMonitor.setScheduler(scheduler);
        return schedulerMonitor;
    }

    @Bean(name = "cpflFisrtJobSchedulerJobFactory")
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

}
