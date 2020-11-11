package br.com.wod.quartz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class JobInfoDTO implements Serializable {

    @JsonProperty("job-name")
    private String jobName;

    @JsonProperty("job-group")
    private String jobGroup;

    @JsonProperty("job-description")
    private String jobDescription;

}
