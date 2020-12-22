package br.com.wod.quartz.application.enelsp.second;

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

import static br.com.wod.quartz.application.enelsp.second.EnelSecondBeans.*;
import static br.com.wod.quartz.application.utils.BeanConstant.QUARTZ_PROPERTIES;
import static br.com.wod.quartz.application.utils.SchedulerConfigUtil.createJobDetail;
import static br.com.wod.quartz.application.utils.SchedulerConfigUtil.setInfo;

@Configuration
public class EnelSpSecondJobConfig {

    @Value("${enelsp.second-job.name}")
    private String jobName;

    @Value("${enelsp.second-job.group}")
    private String jobGroup;

    @Value("${enelsp.second-job.description}")
    private String jobDescription;

    private static final String SCHED_ENELSP_SECOND = "enelsp_second_job_scheduler";

    private static final String JOB_ENELSP_SECOND = "enelsp_second_job";

    @Bean(name = SCHED_ENELSP_SECOND_MONITOR)
    public SchedulerMonitor schedulerMonitor(
            @Qualifier(value = SCHED_ENELSP_SECOND) Scheduler scheduler
    ) {
        SchedulerMonitor schedulerMonitor = new SchedulerMonitorImpl();
        schedulerMonitor.setScheduler(scheduler);
        return schedulerMonitor;
    }

    @Bean(JOB_ENELSP_SECOND_MONITOR)
    public JobDetailMonitor createJobMonitor(
            @Qualifier(JOB_ENELSP_SECOND) JobDetail jobDetail
    ) {
        JobDetailMonitor jobDetailMonitor = new JobDetailMonitorImpl();
        jobDetailMonitor.setJob(jobDetail);
        return jobDetailMonitor;
    }

    @Bean(name = TRIGGER_MONITOR_ENELSP_SECOND)
    public TriggerMonitor createTriggerMonitor() {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        return triggerMonitor;
    }

    @Bean(name = JOB_ENELSP_SECOND)
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetail = createJobDetail(EnelSpSecondJob.class);
        jobDetail.setDurability(true);
        return setInfo(jobDetail, jobName, jobGroup, jobDescription);
    }

    @Bean(name = SCHED_ENELSP_SECOND)
    public SchedulerFactoryBean schedulerFactoryBean(
            @Qualifier(value = QUARTZ_PROPERTIES) Properties properties,
            @Qualifier(value = JOB_ENELSP_SECOND) JobDetail jobDetail,
            @Qualifier(value = "enelSpSecondJobSchedulerJobFactory") JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setJobDetails(jobDetail);
        factory.setQuartzProperties(properties);
        factory.setAutoStartup(false);
        return factory;
    }

    @Bean(name = "enelSpSecondJobSchedulerJobFactory")
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

}
