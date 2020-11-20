package br.com.wod.quartz.dto.jobinfo;

import br.com.wod.quartz.dto.enums.SchedulerStates;
import lombok.Data;

@Data
public class JobStatus {
    private Integer id;
    private String description;

    public JobStatus(SchedulerStates schedulerStates) {
        this.id = schedulerStates.getId();
        this.description = schedulerStates.getDescription();
    }
}
