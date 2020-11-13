package br.com.wod.quartz.resource.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class ValidationStandardError implements Serializable {

    @JsonProperty("status-code")
    private Integer statusCode;

    @JsonProperty("http-status")
    private String httpStatus;

    @JsonProperty("messages")
    private List<String> msg;

    @JsonProperty("timestamp")
    private Long timestamp;
}
