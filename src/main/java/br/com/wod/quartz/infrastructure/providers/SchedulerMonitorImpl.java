package br.com.wod.quartz.infrastructure.providers;

import br.com.wod.quartz.core.adapters.SchedulerMonitor;
import org.quartz.Scheduler;

public class SchedulerMonitorImpl implements SchedulerMonitor {
    private Scheduler scheduler;

    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
