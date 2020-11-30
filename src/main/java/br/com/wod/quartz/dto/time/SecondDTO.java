package br.com.wod.quartz.dto.time;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SecondDTO implements Serializable {

    @JsonProperty("second")
    @NotNull(message = "{time.notnull}")
    @Min(value = 1, message = "{second.min}")
    private Integer second;

}
