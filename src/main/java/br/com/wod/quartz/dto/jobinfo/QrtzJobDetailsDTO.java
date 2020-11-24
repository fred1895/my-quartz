package br.com.wod.quartz.dto.jobinfo;

import br.com.wod.quartz.dto.enums.SchedulerStates;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class QrtzJobDetailsDTO implements Serializable {

    @JsonProperty("job-name")
    String jobName;

    @JsonProperty("job-group")
    String jobGroup;

    @JsonProperty("job-description")
    private String jobDescription;

    @JsonProperty("status")
    private JobStatus status;
}
