package br.com.wod.quartz.core.adapters;

import org.quartz.Scheduler;

public interface SchedulerMonitor {

    Scheduler getScheduler();

    void setScheduler(Scheduler scheduler);
}
