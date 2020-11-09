package br.com.wod.quartz.schedule;

import org.quartz.Trigger;

public interface TriggerMonitor {

	void setTrigger(Trigger trigger);

	Trigger getTrigger();

}
