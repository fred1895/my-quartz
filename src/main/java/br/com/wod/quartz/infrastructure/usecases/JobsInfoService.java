package br.com.wod.quartz.infrastructure.usecases;

import br.com.wod.quartz.api.dto.enums.SchedulerStates;
import br.com.wod.quartz.api.dto.jobinfo.JobStatus;
import br.com.wod.quartz.api.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.api.dto.jobinfo.QrtzTriggersDTO;
import br.com.wod.quartz.api.exception.MySchedulerException;
import br.com.wod.quartz.core.adapters.IJobsInfoService;
import br.com.wod.quartz.core.adapters.JobDetailMonitor;
import br.com.wod.quartz.core.adapters.SchedulerMonitor;
import br.com.wod.quartz.core.adapters.TriggerMonitor;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Value;

import static br.com.wod.quartz.common.utils.JobsBasicServiceUtil.*;

public class JobsInfoService implements IJobsInfoService {

    private SchedulerMonitor schedulerMonitor;
    private JobDetailMonitor jobDetailMonitor;
    private TriggerMonitor triggerMonitor;

    public JobsInfoService(SchedulerMonitor schedulerMonitor, JobDetailMonitor jobDetailMonitor, TriggerMonitor triggerMonitor) {
        this.schedulerMonitor = schedulerMonitor;
        this.jobDetailMonitor = jobDetailMonitor;
        this.triggerMonitor = triggerMonitor;
    }

    @Value("${error.myscheduler.msg}")
    private String errorMsg;

    @Override
    public QrtzJobDetailsDTO getJobInfo() {
        try {
            QrtzJobDetailsDTO qrtzJobDetailsDTO = new QrtzJobDetailsDTO();

            JobDetailImpl jobDetailImpl = (JobDetailImpl) schedulerMonitor.getScheduler()
                    .getJobDetail(jobDetailMonitor.getJob().getKey());

            qrtzJobDetailsDTO.setSchedName(schedulerMonitor.getScheduler().getSchedulerName());
            qrtzJobDetailsDTO.setJobName(jobDetailImpl.getName());
            qrtzJobDetailsDTO.setJobGroup(jobDetailImpl.getGroup());
            qrtzJobDetailsDTO.setJobDescription(jobDetailImpl.getDescription());

            SchedulerStates jobStatus = getJobStatus(schedulerMonitor.getScheduler());
            JobStatus status = new JobStatus(jobStatus);
            qrtzJobDetailsDTO.setStatus(status);

            return qrtzJobDetailsDTO;
        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }

    @Override
    public QrtzTriggersDTO getTriggerInfo() {

        try {
            Trigger trigger = getTriggerFromScheduler(schedulerMonitor.getScheduler(), triggerMonitor);

            boolean simpleTrigger = isSimpleTrigger(trigger);

            if (simpleTrigger) {
                return getSimpleJobDetail(schedulerMonitor.getScheduler(), triggerMonitor);

            } else {
                return getCronJobDetail(schedulerMonitor.getScheduler(), triggerMonitor);
            }

        } catch (SchedulerException e) {
            throw new MySchedulerException(errorMsg + e.getMessage());
        }
    }
}
