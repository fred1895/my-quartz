package br.com.wod.quartz.service;

import br.com.wod.quartz.dto.enums.SchedulerStates;
import br.com.wod.quartz.dto.jobinfo.JobStatus;
import br.com.wod.quartz.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.dto.jobinfo.QrtzJobDetailsNoStsDTO;
import br.com.wod.quartz.entities.QrtzJobDetails;
import br.com.wod.quartz.entities.QrtzTriggers;
import br.com.wod.quartz.repositories.QrtzJobDetailsRepository;
import br.com.wod.quartz.repositories.QrtzTriggersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class SearchService {

    @Autowired
    private QrtzJobDetailsRepository jobDetailsRepository;

    @Autowired
    private QrtzTriggersRepository triggersRepository;

    public List<QrtzJobDetailsDTO> findByGroup(String jobGroup) {
        List<QrtzJobDetails> jobDetails = jobDetailsRepository.findByJobGroup(jobGroup);

        List<QrtzJobDetailsDTO> jobDetailsDTOS = toDto(jobDetails);

        for (QrtzJobDetailsDTO job : jobDetailsDTOS) {
            QrtzTriggers trigger = triggersRepository.findBySchedName(job.getSchedName());
            if (trigger != null) {
                String triggerState = trigger.getTriggerState();
                if (triggerState.equals("WAITING")) {
                    JobStatus jobStatus = new JobStatus(SchedulerStates.WAITING);
                    job.setStatus(jobStatus);
                } else {
                    JobStatus jobStatus = new JobStatus(SchedulerStates.RUNNING);
                    job.setStatus(jobStatus);
                }
            } else {
                JobStatus jobStatus = new JobStatus(SchedulerStates.WAITING);
                job.setStatus(jobStatus);
            }
        }
        return jobDetailsDTOS;
    }

    public List<QrtzJobDetailsNoStsDTO> getWaitingStatus() {
        List<QrtzJobDetailsNoStsDTO> detailsNoStsDTOS = noStsToDto(jobDetailsRepository.findAll());
        List<QrtzJobDetailsNoStsDTO> jobsList = new ArrayList<>();
        for (QrtzJobDetailsNoStsDTO jobDetail : detailsNoStsDTOS) {
            QrtzTriggers triggers = triggersRepository.findBySchedName(jobDetail.getSchedName());
            if (triggers != null && triggers.getTriggerState().equals(SchedulerStates.WAITING.toString())) {
                System.out.println(triggers.getTriggerState());
                jobsList.add(jobDetail);
            }
        }
        return jobsList;
    }

    private List<QrtzJobDetailsDTO> toDto(List<QrtzJobDetails> jobDetails) {
        return jobDetails.stream().map(QrtzJobDetailsDTO::new).collect(toList());
    }

    private List<QrtzJobDetailsNoStsDTO> noStsToDto(List<QrtzJobDetails> jobDetails) {
        return jobDetails.stream().map(QrtzJobDetailsNoStsDTO::new).collect(toList());
    }


}
