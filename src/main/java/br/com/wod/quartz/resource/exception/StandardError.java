package br.com.wod.quartz.resource.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StandardError implements Serializable {

    private Integer status;
    private String msg;
    private Long timeStamp;
}
