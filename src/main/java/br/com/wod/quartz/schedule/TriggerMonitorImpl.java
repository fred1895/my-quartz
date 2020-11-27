package br.com.wod.quartz.schedule;

import lombok.Data;
import org.quartz.Trigger;
@Data
public class TriggerMonitorImpl implements TriggerMonitor {

    private Trigger trigger;
/*
    @Override
    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

 */
}
