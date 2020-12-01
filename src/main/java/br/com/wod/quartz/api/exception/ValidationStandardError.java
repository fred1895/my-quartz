package br.com.wod.quartz.api.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class ValidationStandardError implements Serializable {

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("status-code")
    private Integer statusCode;

    @JsonProperty("http-status")
    private String httpStatus;

    @JsonProperty("messages")
    private List<String> msg;

}
