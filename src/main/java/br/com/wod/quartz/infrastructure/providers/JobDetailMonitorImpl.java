package br.com.wod.quartz.infrastructure.providers;

import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import org.quartz.JobDetail;
import org.springframework.stereotype.Service;

@Service
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
