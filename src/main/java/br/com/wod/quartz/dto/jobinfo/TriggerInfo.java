package br.com.wod.quartz.dto.jobinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@Component
public abstract class TriggerInfo implements Serializable {

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
