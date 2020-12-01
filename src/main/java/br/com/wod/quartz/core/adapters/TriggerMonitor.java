package br.com.wod.quartz.core.adapters;

import org.quartz.Trigger;

public interface TriggerMonitor {

    void setTrigger(Trigger trigger);

    Trigger getTrigger();

}
