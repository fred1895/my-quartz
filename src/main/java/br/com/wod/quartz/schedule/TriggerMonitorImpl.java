package br.com.wod.quartz.schedule;

import org.quartz.Trigger;

public class TriggerMonitorImpl implements TriggerMonitor {

    private Trigger trigger;

    @Override
    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

}
