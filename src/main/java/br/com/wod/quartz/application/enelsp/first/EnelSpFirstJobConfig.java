package br.com.wod.quartz.application.enelsp.first;

import br.com.wod.quartz.common.schedule.AutowiringSpringBeanJobFactory;
import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import br.com.wod.quartz.core.adapters.SchedulerMonitor;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import br.com.wod.quartz.core.entities.JobDetailMonitorImpl;
import br.com.wod.quartz.core.entities.SchedulerMonitorImpl;
import br.com.wod.quartz.core.entities.TriggerMonitorImpl;
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

import static br.com.wod.quartz.application.enelsp.first.EnelFirstBeans.*;
import static br.com.wod.quartz.application.utils.BeanConstant.QUARTZ_PROPERTIES;
import static br.com.wod.quartz.application.utils.SchedulerConfigUtil.createJobDetail;
import static br.com.wod.quartz.application.utils.SchedulerConfigUtil.setInfo;

@Configuration
public class EnelSpFirstJobConfig {

    @Value("${enelsp.first-job.name}")
    private String jobName;

    @Value("${enelsp.first-job.group}")
    private String jobGroup;

    @Value("${enelsp.first-job.description}")
    private String jobDescription;

    private static final String SCHED_ENELSP_FIRST = "enelsp_first_job_scheduler";

    private static final String JOB_ENELSP_FIRST = "enelsp_first_job";

    @Bean(name = JOB_ENELSP_FIRST)
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetail = createJobDetail(EnelSpFirstJob.class);
        jobDetail.setDurability(true);
        return setInfo(jobDetail, jobName, jobGroup, jobDescription);
    }

    @Bean(name = JOB_ENELSP_FIRST_MONITOR)
    public JobDetailMonitor createJobMonitor(
            @Qualifier(JOB_ENELSP_FIRST) JobDetail jobDetail
    ) {
        JobDetailMonitor jobDetailMonitor = new JobDetailMonitorImpl();
        jobDetailMonitor.setJob(jobDetail);
        return jobDetailMonitor;
    }

    @Bean(name = TRIGGER_MONITOR_ENELSP_FIRST)
    public TriggerMonitor createTriggerMonitor() {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        return triggerMonitor;
    }

    @Bean(name = SCHED_ENELSP_FIRST)
    public SchedulerFactoryBean schedulerFactoryBean(
            @Qualifier(value = QUARTZ_PROPERTIES) Properties properties,
            @Qualifier(value = JOB_ENELSP_FIRST) JobDetail jobDetail,
            @Qualifier(value = "enelSpFirstJobSchedulerJobFactory") JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobDetails(jobDetail);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(properties);
        factory.setAutoStartup(false);
        return factory;
    }

    @Bean(name = "enelSpFirstJobSchedulerJobFactory")
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean(name = SCHED_ENELSP_FIRST_MONITOR)
    public SchedulerMonitor schedulerMonitor(
            @Qualifier(SCHED_ENELSP_FIRST) Scheduler scheduler
    ) {
        SchedulerMonitor schedulerMonitor = new SchedulerMonitorImpl();
        schedulerMonitor.setScheduler(scheduler);
        return schedulerMonitor;
    }

}
