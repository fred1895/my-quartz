package br.com.wod.quartz.api.dto.enums;

import lombok.Getter;

@Getter
public enum SchedulerStates {
    WAITING(1, "PARADO"),
    PAUSED(2, "PAUSADO"),
    RUNNING(3, "RODANDO");

    private int id;

    private String description;

    private SchedulerStates(int id, String description) {
        this.id = id;
        this.description = description;
    }

}
