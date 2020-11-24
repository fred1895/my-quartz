package br.com.wod.quartz.resource;

import br.com.wod.quartz.cpfl.service.CpflSecondJobService;
import br.com.wod.quartz.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.dto.jobinfo.QrtzTriggersDTO;
import br.com.wod.quartz.dto.time.DailyDTO;
import br.com.wod.quartz.dto.time.HourDTO;
import br.com.wod.quartz.dto.time.MinuteDTO;
import br.com.wod.quartz.dto.time.SecondDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/${cpfl.second-job.group}/job/${cpfl.second-job.name}")
@CrossOrigin("*")
public class CpflSecondJobResource {

    @Autowired
    private CpflSecondJobService service;

    @GetMapping("/cron-info")
    @ResponseStatus(HttpStatus.OK)
    public QrtzTriggersDTO getInfo() {
        return service.getTriggerInfo();
    }

    @GetMapping("/job-info")
    @ResponseStatus(HttpStatus.OK)
    public QrtzJobDetailsDTO getSimpleInfo() {
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
