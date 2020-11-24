package br.com.wod.quartz.dto.jobinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class QrtzTriggersDTOSimpleDTO extends QrtzTriggersDTO {

    @JsonProperty("repeat-interval")
    private Long repeatInterval;

    @JsonProperty("times-triggered")
    private Integer timesTriggered;
}
