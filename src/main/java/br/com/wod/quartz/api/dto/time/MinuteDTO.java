package br.com.wod.quartz.api.dto.time;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MinuteDTO implements Serializable {

    @JsonProperty("minute")
    @NotNull(message = "{time.notnull}")
    @Min(value = 1, message = "{minute.min}")
    private Integer minute;

}
