package br.com.wod.quartz.resource;

import br.com.wod.quartz.dto.jobinfo.JobInfo;
import br.com.wod.quartz.service.AllJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/all-jobs")
public class AllJobsResource {

    @Autowired
    private AllJobsService service;

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public List<JobInfo> jobsInfo() {
        return service.getJobInfos();
    }

    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public void startJobs() {
        service.startAllJobs();
    }

    @GetMapping("/pause")
    @ResponseStatus(HttpStatus.OK)
    public void pauseJobs() {
        service.pauseAllJobs();
    }

    @GetMapping("/resume")
    @ResponseStatus(HttpStatus.OK)
    public void resumeJobs() {
        service.resumeAllJobs();
    }

}
