package br.com.wod.quartz.core.usecases;

import br.com.wod.quartz.api.dto.enums.SchedulerStates;
import br.com.wod.quartz.api.dto.jobinfo.JobStatus;
import br.com.wod.quartz.api.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.api.dto.jobinfo.QrtzTriggersDTO;
import br.com.wod.quartz.api.exception.MySchedulerException;
import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static br.com.wod.quartz.common.utils.JobsBasicServiceUtil.*;

@Service
@Slf4j
public class JobsInfoServiceQrtz {

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    public QrtzJobDetailsDTO getJobInfo(Scheduler scheduler, JobDetail jobDetail) {
        try {
            QrtzJobDetailsDTO qrtzJobDetailsDTO = new QrtzJobDetailsDTO();

            JobDetailImpl jobDetailImpl = (JobDetailImpl) scheduler
                    .getJobDetail(jobDetail.getKey());

            qrtzJobDetailsDTO.setSchedName(scheduler.getSchedulerName());
            qrtzJobDetailsDTO.setJobName(jobDetailImpl.getName());
            qrtzJobDetailsDTO.setJobGroup(jobDetailImpl.getGroup());
            qrtzJobDetailsDTO.setJobDescription(jobDetailImpl.getDescription());

            SchedulerStates jobStatus = getJobStatus(scheduler);
            JobStatus status = new JobStatus(jobStatus);
            qrtzJobDetailsDTO.setStatus(status);

            return qrtzJobDetailsDTO;
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    public QrtzTriggersDTO getTriggerInfo(Scheduler scheduler, TriggerMonitor triggerMonitor) {

        try {
            Trigger trigger = getTriggerFromScheduler(scheduler, triggerMonitor);

            boolean simpleTrigger = isSimpleTrigger(trigger);

            if (simpleTrigger) {
                return getSimpleJobDetail(scheduler, triggerMonitor);

            } else {
                return getCronJobDetail(scheduler, triggerMonitor);
            }

        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }
}
