package br.com.wod.quartz.resource.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StandardError implements Serializable {

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("status-code")
    private Integer statusCode;

    @JsonProperty("http-status")
    private String httpStatus;

    @JsonProperty("message")
    private String msg;

}
