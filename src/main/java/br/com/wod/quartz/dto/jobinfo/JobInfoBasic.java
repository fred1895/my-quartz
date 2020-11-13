package br.com.wod.quartz.dto.jobinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@Component
public abstract class JobInfoBasic implements Serializable {

    @JsonProperty("job-name")
    String jobName;

    @JsonProperty("job-group")
    String jobGroup;

    @JsonProperty("job-description")
    private String jobDescription;

    @JsonProperty("previous-fire-time")
    @JsonFormat(
            pattern = "yyyy-MM-dd@HH:mm:ss",
            timezone="Brazil/East",
            locale = "pt_BR",
            shape = JsonFormat.Shape.STRING)
    private Date previousFireTime;

    @JsonProperty("next-fire-time")
    @JsonFormat(
            pattern = "yyyy-MM-dd@HH:mm:ss",
            locale = "pt_BR",
            timezone="Brazil/East",
            shape = JsonFormat.Shape.STRING)
    private Date nextFireTime;

}
