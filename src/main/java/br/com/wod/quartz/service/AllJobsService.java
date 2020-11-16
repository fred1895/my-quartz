package br.com.wod.quartz.service;

import br.com.wod.quartz.cpfl.service.CpflFirstJobService;
import br.com.wod.quartz.cpfl.service.CpflSecondJobService;
import br.com.wod.quartz.dto.jobinfo.JobInfo;
import br.com.wod.quartz.enelsp.service.EnelSpFirstJobService;
import br.com.wod.quartz.enelsp.service.EnelSpSecondJobService;
import br.com.wod.quartz.resource.exception.MySchedulerException;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AllJobsService {

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    @Resource
    private Scheduler scheduler;

    @Autowired
    private EnelSpFirstJobService enelSpFirstJobService;

    @Autowired
    private EnelSpSecondJobService enelSpSecondJobService;

    @Autowired
    private CpflFirstJobService cpflFirstJobService;

    @Autowired
    private CpflSecondJobService cpflSecondJobService;

    public List<JobInfo> getJobInfos() {
        return Arrays.asList(
                enelSpFirstJobService.getJobInfo(),
                enelSpSecondJobService.getJobInfo(),
                cpflFirstJobService.getJobInfo(),
                cpflSecondJobService.getJobInfo()
        );
    }

    public void startAllJobs() {
        enelSpFirstJobService.startJob();
        enelSpSecondJobService.startJob();
        cpflFirstJobService.startJob();
        cpflSecondJobService.startJob();
    }

    public void pauseAllJobs() {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public void resumeAllJobs() {
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

}
