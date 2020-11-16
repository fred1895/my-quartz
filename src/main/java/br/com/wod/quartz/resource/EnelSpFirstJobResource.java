package br.com.wod.quartz.resource;

import br.com.wod.quartz.dto.jobinfo.JobInfo;
import br.com.wod.quartz.dto.jobinfo.TriggerInfo;
import br.com.wod.quartz.dto.time.DailyDTO;
import br.com.wod.quartz.dto.time.HourDTO;
import br.com.wod.quartz.dto.time.MinuteDTO;
import br.com.wod.quartz.dto.time.SecondDTO;
import br.com.wod.quartz.enelsp.service.EnelSpFirstJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/enelsp/job/first")
public class EnelSpFirstJobResource {

    @Autowired
    private EnelSpFirstJobService service;

    @GetMapping("/cron-info")
    @ResponseStatus(HttpStatus.OK)
    public TriggerInfo getInfo() {
        return service.getTriggerInfo();
    }

    @GetMapping("/job-info")
    @ResponseStatus(HttpStatus.OK)
    public JobInfo getSimpleInfo() {
        return service.getJobInfo();
    }

    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public void startJob() {
        service.startJob();
    }

    @GetMapping("/pause")
    @ResponseStatus(HttpStatus.OK)
    public void pauseJob() {
        service.pauseJob();
    }

    @GetMapping("/resume")
    @ResponseStatus(HttpStatus.OK)
    public void resumeJob() {
        service.resumeJob();
    }

    @GetMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteJob() {
        service.deleteJob();
    }

    @PostMapping("/config/dia")
    @ResponseStatus(HttpStatus.CREATED)
    public void dailyConfig(@RequestBody @Valid DailyDTO time) {
        service.dailyJobConfig(time);
    }

    @PostMapping("/config/hora")
    @ResponseStatus(HttpStatus.CREATED)
    public void hourConfig(@RequestBody @Valid HourDTO time) {
        service.hourJobConfig(time);
    }

    @PostMapping("/config/minuto")
    @ResponseStatus(HttpStatus.CREATED)
    public void minuteConfig(@RequestBody @Valid MinuteDTO time) {
        service.minuteJobConfig(time);
    }

    @PostMapping("/config/segundo")
    @ResponseStatus(HttpStatus.CREATED)
    public void secondConfig(@RequestBody @Valid SecondDTO time) {
        service.secondJobConfig(time);
    }

}
