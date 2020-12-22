package br.com.wod.quartz.api.facade;

import br.com.wod.quartz.api.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.api.dto.jobinfo.QrtzTriggersDTO;
import br.com.wod.quartz.api.dto.time.DailyDTO;
import br.com.wod.quartz.api.dto.time.HourDTO;
import br.com.wod.quartz.api.dto.time.MinuteDTO;
import br.com.wod.quartz.api.dto.time.SecondDTO;
import br.com.wod.quartz.core.adapters.IJobsConfigService;
import br.com.wod.quartz.core.adapters.IJobsInfoService;
import br.com.wod.quartz.core.adapters.IJobsPlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static br.com.wod.quartz.application.cpfl.first.CpflFirstBeans.*;

@Service
@Slf4j
public class CpflFirstJobFacade {

    @Autowired
    @Qualifier(value = PLAYER_CPFL_FIRST)
    private IJobsPlayerService playerService;

    @Autowired
    @Qualifier(value = CONFIG_CPFL_FIRST)
    private IJobsConfigService configService;

    @Autowired
    @Qualifier(value = INFO_CPFL_FIRST)
    private IJobsInfoService infoService;

    public void startJob() {
        playerService.start();
    }

    public QrtzJobDetailsDTO getJobInfo() {
        return infoService.getJobInfo();
    }

    public QrtzTriggersDTO getTriggerInfo() {
        return infoService.getTriggerInfo();
    }

    public void resumeJob() {
        playerService.resume();
    }

    public void pauseJob() {
        playerService.pause();
    }

    public void deleteJob() {
        playerService.delete();
    }

    public void dailyJobConfig(DailyDTO dailyDTO) {
        configService.dailyConfig(dailyDTO);
    }

    public void hourJobConfig(HourDTO hourDTO) {
        configService.hourConfig(hourDTO);
    }

    public void minuteJobConfig(MinuteDTO minuteDTO) {
        configService.minuteConfig(minuteDTO);
    }

    public void secondJobConfig(SecondDTO secondDTO) {
        configService.secondConfig(secondDTO);
    }

}
