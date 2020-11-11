package br.com.wod.quartz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TimeDTO {
    @JsonProperty("hour")
    private Integer hour;

    @JsonProperty("minute")
    private Integer minute;

    @JsonProperty("second")
    private Integer second;
}
