package br.com.wod.quartz.dto.time;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class HourDTO implements Serializable {

    @JsonProperty("hour")
    @NotNull(message = "{time.notnull}")
    @Min(value = 1, message = "{hour.min}")
    private Integer hour;

}
