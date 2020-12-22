package br.com.wod.quartz.api.facade;

import br.com.wod.quartz.api.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.api.dto.jobinfo.QrtzTriggersDTO;
import br.com.wod.quartz.api.dto.time.DailyDTO;
import br.com.wod.quartz.api.dto.time.HourDTO;
import br.com.wod.quartz.api.dto.time.MinuteDTO;
import br.com.wod.quartz.api.dto.time.SecondDTO;
import br.com.wod.quartz.core.adapters.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static br.com.wod.quartz.application.enelsp.second.EnelSecondBeans.*;

@Service
@Slf4j
public class EnelSpSecondJobFacade {
    @Autowired
    @Qualifier(value = PLAYER_ENELSP_SECOND)
    private IJobsPlayerService playerService;

    @Autowired
    @Qualifier(value = CONFIG_ENELSP_SECOND)
    private IJobsConfigService configService;

    @Autowired
    @Qualifier(value = INFO_ENELSP_SECOND)
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
