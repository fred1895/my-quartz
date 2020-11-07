package br.com.wod.quartz.trigger;

import br.com.wod.quartz.dto.TimeDTO;
import org.quartz.*;

public class Schedule {

    public static Trigger dailySchedule(TimeDTO timeDTO) {
        return DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(timeDTO.getHour(), timeDTO.getMinute()))
                .build();
    }

    public static Trigger simpleSchedule() {
        return TriggerBuilder.newTrigger().withIdentity("MyTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withRepeatCount(5)
                        .withIntervalInSeconds(3))
                .build();
    }

}
