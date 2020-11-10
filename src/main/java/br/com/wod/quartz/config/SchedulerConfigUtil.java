package br.com.wod.quartz.config;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

public class SchedulerConfigUtil {

    public static JobDetailFactoryBean createJobDetail(Class<? extends Job> jobClass) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(false);
        return factoryBean;
    }

    public static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs,
                                                          int repeatCount) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(3000L);
        factoryBean.setRepeatInterval(pollFrequencyMs);
        factoryBean.setRepeatCount(repeatCount);
        factoryBean
                .setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT);// in case of misfire, ignore all missed triggers and continue
        return factoryBean;
    }
/*
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory,
                                                     @Qualifier("jobTrigger") Trigger sampleJobTrigger) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setTriggers(sampleJobTrigger);
        factory.setAutoStartup(false);
        return factory;
    }

    @Bean
    public JobDetailFactoryBean jobDetail() {
        return createJobDetail(MyJob.class);
    }

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean(name = "jobTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("jobDetail") JobDetail jobDetail
    ) {
        return createTrigger(jobDetail, 10000L, 5);
    }

    @Bean(name = "triggerMonitor")
    public TriggerMonitor createTriggerMonitor(@Qualifier("jobTrigger") Trigger trigger) {
        TriggerMonitor triggerMonitor = new TriggerMonitorImpl();
        triggerMonitor.setTrigger(trigger);
        return triggerMonitor;
    }
*/
}
