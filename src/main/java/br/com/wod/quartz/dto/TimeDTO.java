package br.com.wod.quartz.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TimeDTO {
    private Integer hour;
    private Integer minute;
    private Integer second;
}
