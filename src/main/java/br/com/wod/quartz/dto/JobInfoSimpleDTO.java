package br.com.wod.quartz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class JobInfoSimpleDTO extends JobInfoBasic{

    @JsonProperty("repeat-interval")
    private Long repeatInterval;

    @JsonProperty("times-triggered")
    private Integer timesTriggered;
}
