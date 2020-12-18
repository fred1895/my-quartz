package br.com.wod.quartz.configurations.utils;

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

    public static JobDetailFactoryBean setInfo(
            JobDetailFactoryBean jobDetail,
            String jobName,
            String jobGroup,
            String jobDescription) {

        jobDetail.setName(jobName);
        jobDetail.setGroup(jobGroup);
        jobDetail.setDescription(jobDescription);

        return jobDetail;
    }

}
