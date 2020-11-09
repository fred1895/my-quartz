package br.com.wod.quartz.resource;

import br.com.wod.quartz.dto.SchedulerConfigParam;
import br.com.wod.quartz.service.EnelSpScheduleService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enelsp-agendamentos")
public class EnelSpScheduleResource {

    @Autowired
    private EnelSpScheduleService service;

    @GetMapping("/inicia")
    @ResponseStatus(HttpStatus.OK)
    public void startJob() throws SchedulerException {
        service.startJob();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchedulerConfigParam postConfig(@RequestBody SchedulerConfigParam configParam) throws SchedulerException {
        return service.postConfig(configParam);
    }

}
