package br.com.wod.quartz.dto.enums;

import lombok.Getter;

public enum SchedulerStates {
    WAITING(1, "EM ESPERA"),
    RUNNING (2, "RODANDO");

    @Getter
    private int id;

    @Getter
    private String description;

    private SchedulerStates(int id, String description) {
        this.id = id;
        this.description = description;
    }

}
