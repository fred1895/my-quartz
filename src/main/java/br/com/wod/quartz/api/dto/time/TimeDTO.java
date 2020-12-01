package br.com.wod.quartz.api.dto.time;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class TimeDTO implements Serializable {

    @JsonProperty("hour")
    private Integer hour;

    @JsonProperty("minute")
    private Integer minute;

    @JsonProperty("second")
    private Integer second;
}
