package br.com.wod.quartz.resource;

import br.com.wod.quartz.dto.TimeDTO;
import br.com.wod.quartz.enelsp.service.EnelSpSecondJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enelsp/job/second")
public class EnelSpSecondJobResource {

    @Autowired
    private EnelSpSecondJobService service;

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
    public void dailyConfig(@RequestBody TimeDTO time) {
        service.dailyJobConfig(time);
    }

    @PostMapping("/config/hora")
    @ResponseStatus(HttpStatus.CREATED)
    public void hourConfig(@RequestBody TimeDTO time) {
        service.hourJobConfig(time);
    }

    @PostMapping("/config/minuto")
    @ResponseStatus(HttpStatus.CREATED)
    public void minuteConfig(@RequestBody TimeDTO time) {
        service.minuteJobConfig(time);
    }

    @PostMapping("/config/segundo")
    @ResponseStatus(HttpStatus.CREATED)
    public void secondConfig(@RequestBody TimeDTO time) {
        service.secondJobConfig(time);
    }

}
