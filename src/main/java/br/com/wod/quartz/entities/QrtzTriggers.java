package br.com.wod.quartz.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "QRTZ_TRIGGERS")
@Data
public class QrtzTriggers implements Serializable {

    @Id
    @Column(name = "TRIGGER_NAME")
    private String triggerName;

    @Column(name = "TRIGGER_GROUP")
    private String triggerGroup;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NEXT_FIRE_TIME")
    private Long nextFireTime;

    @Column(name = "PREV_FIRE_TIME")
    private Long prevFireTime;

    @Column(name = "TRIGGER_STATE")
    private String triggerState;

    @Column(name = "TRIGGER_TYPE")
    private String triggerType;

    @Column(name = "START_TIME")
    private Long startTime;

    @Column(name = "END_TIME")
    private Long endTime;

    @Column(name = "CALENDAR_NAME")
    private String calendarName;
}
