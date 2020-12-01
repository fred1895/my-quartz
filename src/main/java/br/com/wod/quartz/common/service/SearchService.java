package br.com.wod.quartz.common.service;

import br.com.wod.quartz.api.facade.CpflFirstJobFacade;
import br.com.wod.quartz.api.facade.CpflSecondJobFacade;
import br.com.wod.quartz.api.dto.enums.SchedulerStates;
import br.com.wod.quartz.api.dto.jobinfo.JobStatus;
import br.com.wod.quartz.api.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.api.dto.jobinfo.QrtzJobDetailsNoStsDTO;
import br.com.wod.quartz.api.facade.EnelSpFirstJobFacade;
import br.com.wod.quartz.api.facade.EnelSpSecondJobFacade;
import br.com.wod.quartz.core.entities.QrtzJobDetails;
import br.com.wod.quartz.core.entities.QrtzTriggers;
import br.com.wod.quartz.infrastructure.dao.QrtzJobDetailsDAO;
import br.com.wod.quartz.infrastructure.dao.QrtzTriggersDAO;
import br.com.wod.quartz.api.exception.MySchedulerException;
import br.com.wod.quartz.api.exception.QrtzObjectNotFoundException;
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
    private QrtzJobDetailsDAO jobDetailsRepository;

    @Autowired
    private QrtzTriggersDAO triggersRepository;

    @Autowired
    private EnelSpFirstJobFacade enelSpFirstJobFacade;

    @Autowired
    private EnelSpSecondJobFacade enelSpSecondJobFacade;

    @Autowired
    private CpflFirstJobFacade cpflFirstJobFacade;

    @Autowired
    private CpflSecondJobFacade cpflSecondJobFacade;

    public List<QrtzJobDetailsDTO> findByGroup(String jobGroup) {
        List<QrtzJobDetails> jobDetails = jobDetailsRepository.findByJobGroup(jobGroup);
        if (jobDetails.isEmpty()) {
            throw new QrtzObjectNotFoundException();
        }
        List<QrtzJobDetailsDTO> jobDetailsDTOS = toDto(jobDetails);
        setStatus(jobDetailsDTOS);
        return jobDetailsDTOS;
    }

    public List<QrtzJobDetailsDTO> findAll() {
        QrtzJobDetailsDTO jobInfo = enelSpFirstJobFacade.getJobInfo();
        QrtzJobDetailsDTO jobInfo1 = enelSpSecondJobFacade.getJobInfo();
        QrtzJobDetailsDTO jobInfo2 = cpflFirstJobFacade.getJobInfo();
        QrtzJobDetailsDTO jobInfo3 = cpflSecondJobFacade.getJobInfo();
        List<QrtzJobDetailsDTO> qrtzJobDetailsDTOS = Arrays.asList(jobInfo, jobInfo1, jobInfo2, jobInfo3);
        return qrtzJobDetailsDTOS;
    }

    public List<QrtzJobDetailsDTO> findByStatus(String status) {
        List<QrtzJobDetailsDTO> qrtzJobDetailsDTOS = findAll();
        String statusUpp = status.toUpperCase();
        SchedulerStates schedulerStates = SchedulerStates.valueOf(statusUpp);
        List<QrtzJobDetailsDTO> qrtzJobDetails = new ArrayList<>();

        for (QrtzJobDetailsDTO job : qrtzJobDetailsDTOS) {
            if (job.getStatus().getId() == schedulerStates.getId()) {
                qrtzJobDetails.add(job);
            }
        }
        if (qrtzJobDetails.isEmpty()) throw new QrtzObjectNotFoundException();
        return qrtzJobDetails;
    }


    public List<QrtzJobDetailsNoStsDTO> getByStatus(String status) {
        String statusUpp = status.toUpperCase();
        List<QrtzTriggers> triggers = triggersRepository.findByTriggerState(statusUpp);

        List<QrtzJobDetails> jobs = new ArrayList<>();

        for (QrtzTriggers trig : triggers) {
            String schedName = trig.getSchedName();

            QrtzJobDetails jobDetail = jobDetailsRepository
                    .findBySchedName(schedName);

            jobs.add(jobDetail);
        }

        return noStsToDto(jobs);
    }


    private void setStatus(List<QrtzJobDetailsDTO> jobDetailsDTOS) {
        for (QrtzJobDetailsDTO job : jobDetailsDTOS) {
            QrtzTriggers trigger = triggersRepository.findBySchedName(job.getSchedName());
            if (trigger != null) {
                String triggerState = trigger.getTriggerState();
                if (triggerState.equals("WAITING")) {
                    JobStatus jobStatus = new JobStatus(SchedulerStates.WAITING);
                    job.setStatus(jobStatus);
                } else if (triggerState.equals("ACQUIRED")) {
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
