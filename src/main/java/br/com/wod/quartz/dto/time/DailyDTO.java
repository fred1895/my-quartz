package br.com.wod.quartz.dto.time;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DailyDTO implements Serializable {

    @JsonProperty("hour")
    @NotNull(message = "{time.notnull}")
    @Min(value = 0, message = "{daily-hour.min}")
    @Max(value = 23, message = "{hour.max}")
    private Integer hour;

    @JsonProperty("minute")
    @NotNull(message = "{time.notnull}")
    @Min(value = 0, message = "{daily-minute.min}")
    @Max(value = 59, message = "{minute.max}")
    private Integer minute;

}
