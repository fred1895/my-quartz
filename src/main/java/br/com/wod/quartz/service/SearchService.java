package br.com.wod.quartz.service;

import br.com.wod.quartz.cpfl.service.CpflFirstJobService;
import br.com.wod.quartz.cpfl.service.CpflSecondJobService;
import br.com.wod.quartz.dto.enums.SchedulerStates;
import br.com.wod.quartz.dto.jobinfo.JobStatus;
import br.com.wod.quartz.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.dto.jobinfo.QrtzJobDetailsNoStsDTO;
import br.com.wod.quartz.enelsp.service.EnelSpFirstJobService;
import br.com.wod.quartz.enelsp.service.EnelSpSecondJobService;
import br.com.wod.quartz.entities.QrtzJobDetails;
import br.com.wod.quartz.entities.QrtzTriggers;
import br.com.wod.quartz.repositories.QrtzJobDetailsRepository;
import br.com.wod.quartz.repositories.QrtzTriggersRepository;
import br.com.wod.quartz.resource.exception.MySchedulerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class SearchService {

    @Autowired
    private QrtzJobDetailsRepository jobDetailsRepository;

    @Autowired
    private QrtzTriggersRepository triggersRepository;

    @Autowired
    private EnelSpFirstJobService enelSpFirstJobService;

    @Autowired
    private EnelSpSecondJobService enelSpSecondJobService;

    @Autowired
    private CpflFirstJobService cpflFirstJobService;

    @Autowired
    private CpflSecondJobService cpflSecondJobService;

    public List<QrtzJobDetailsDTO> findByGroup(String jobGroup) {
        List<QrtzJobDetails> jobDetails = jobDetailsRepository.findByJobGroup(jobGroup);
        List<QrtzJobDetailsDTO> jobDetailsDTOS = toDto(jobDetails);
        setStatus(jobDetailsDTOS);
        return jobDetailsDTOS;
    }

    public List<QrtzJobDetailsDTO> findAll() {
        QrtzJobDetailsDTO jobInfo = enelSpFirstJobService.getJobInfo();
        QrtzJobDetailsDTO jobInfo1 = enelSpSecondJobService.getJobInfo();
        QrtzJobDetailsDTO jobInfo2 = cpflFirstJobService.getJobInfo();
        QrtzJobDetailsDTO jobInfo3 = cpflSecondJobService.getJobInfo();
        return Arrays.asList(jobInfo, jobInfo1, jobInfo2, jobInfo3);
    }

    private void setStatus(List<QrtzJobDetailsDTO> jobDetailsDTOS) {
        for (QrtzJobDetailsDTO job : jobDetailsDTOS) {
            QrtzTriggers trigger = triggersRepository.findBySchedName(job.getSchedName());
            if (trigger != null) {
                String triggerState = trigger.getTriggerState();
                if (triggerState.equals("WAITING")) {
                    JobStatus jobStatus = new JobStatus(SchedulerStates.WAITING);
                    job.setStatus(jobStatus);
                } else if (triggerState.equals("ACQUIRED")){
                    JobStatus jobStatus = new JobStatus(SchedulerStates.RUNNING);
                    job.setStatus(jobStatus);
                } else {
                    throw new MySchedulerException("Unexpected error");
                }
            } else {
                JobStatus jobStatus = new JobStatus(SchedulerStates.WAITING);
                job.setStatus(jobStatus);
            }
        }
    }

    private List<QrtzJobDetailsDTO> toDto(List<QrtzJobDetails> jobDetails) {
        return jobDetails.stream().map(QrtzJobDetailsDTO::new).collect(toList());
    }

    private List<QrtzJobDetailsNoStsDTO> noStsToDto(List<QrtzJobDetails> jobDetails) {
        return jobDetails.stream().map(QrtzJobDetailsNoStsDTO::new).collect(toList());
    }

}
