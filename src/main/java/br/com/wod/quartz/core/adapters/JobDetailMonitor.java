package br.com.wod.quartz.core.adapters;

import org.quartz.JobDetail;

public interface JobDetailMonitor {

    JobDetail getJob();

    void setJob(JobDetail jobDetail);
}
