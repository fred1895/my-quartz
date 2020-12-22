package br.com.wod.quartz.core.adapters;

import br.com.wod.quartz.api.dto.time.DailyDTO;
import br.com.wod.quartz.api.dto.time.HourDTO;
import br.com.wod.quartz.api.dto.time.MinuteDTO;
import br.com.wod.quartz.api.dto.time.SecondDTO;

public interface IJobsConfigService {
    void dailyConfig(DailyDTO dailyDTO);

    void hourConfig(HourDTO hourDTO);

    void minuteConfig(MinuteDTO minuteDTO);

    void secondConfig(SecondDTO secondDTO);
}
