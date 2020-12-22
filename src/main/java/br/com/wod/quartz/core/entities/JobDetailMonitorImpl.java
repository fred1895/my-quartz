package br.com.wod.quartz.core.entities;

import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import org.quartz.JobDetail;

public class JobDetailMonitorImpl implements JobDetailMonitor {

    private JobDetail jobDetail;

    @Override
    public JobDetail getJob() {
        return this.jobDetail;
    }

    @Override
    public void setJob(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }
}
