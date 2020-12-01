package br.com.wod.quartz.api.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
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
