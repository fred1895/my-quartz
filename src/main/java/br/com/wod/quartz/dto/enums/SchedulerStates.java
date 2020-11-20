package br.com.wod.quartz.dto.enums;

import lombok.Getter;

public enum SchedulerStates {
    STOPPED (1, "PARADO"),
    RUNNING (2, "RODANDO"),
    PAUSED (3, "PAUSADO");

    @Getter
    private int id;

    @Getter
    private String description;

    private SchedulerStates(int id, String description) {
        this.id = id;
        this.description = description;
    }

}
