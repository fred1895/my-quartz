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

    public static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(2000L);
        factoryBean.setRepeatInterval(10000L);
        factoryBean
                .setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT);// in case of misfire, ignore all missed triggers and continue
        return factoryBean;
    }

}
