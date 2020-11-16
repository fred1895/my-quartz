package br.com.wod.quartz.dto.jobinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@Component
public class JobInfo implements Serializable {

    @JsonProperty("job-name")
    String jobName;

    @JsonProperty("job-group")
    String jobGroup;

    @JsonProperty("job-description")
    private String jobDescription;

    @JsonProperty("status")
    private String status;
}
