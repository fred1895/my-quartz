package br.com.wod.quartz.config;

import br.com.wod.quartz.jobs.MyJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class SchedulerConfig {

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        return factory.getScheduler();
    }

    @Bean
    public JobDetail enelJob() {
        return JobBuilder.newJob(MyJob.class)
                .withIdentity("FirstJob", "group1").build();
    }

    @Bean
    public Trigger simpleSchedule() {
        return TriggerBuilder.newTrigger().withIdentity("MyTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .repeatForever()
                        .withIntervalInSeconds(3))
                .build();
    }
}
