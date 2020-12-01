package br.com.wod.quartz.api.dto.jobinfo;

import br.com.wod.quartz.core.entities.QrtzJobDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Component
public class QrtzJobDetailsNoStsDTO implements Serializable {

    @JsonProperty("sched-name")
    private String schedName;

    @JsonProperty("job-name")
    private String jobName;

    @JsonProperty("job-group")
    private String jobGroup;

    @JsonProperty("job-description")
    private String jobDescription;

    public QrtzJobDetailsNoStsDTO(QrtzJobDetails jobDetails) {
        this.schedName = jobDetails.getSchedName();
        this.jobName = jobDetails.getJobName();
        this.jobGroup = jobDetails.getJobGroup();
        this.jobDescription = jobDetails.getDescription();
    }
}
