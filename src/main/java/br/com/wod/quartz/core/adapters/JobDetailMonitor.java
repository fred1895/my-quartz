package br.com.wod.quartz.core.adapters;

import org.quartz.JobDetail;
import org.springframework.stereotype.Service;

@Service
public interface JobDetailMonitor {

    JobDetail getJob();

    void setJob(JobDetail jobDetail);
}
