package br.com.wod.quartz.config;

import br.com.wod.quartz.jobs.MyJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        return factory.getScheduler();
    }

    @Bean
    public JobDetail enelJob() {
        return JobBuilder.newJob(MyJob.class).withIdentity("FirstJob", "group1").build();
    }
}
