package br.com.wod.quartz.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "QRTZ_SIMPLE_TRIGGERS")
public class QrtzSimpleTriggers implements Serializable {

    @Id
    @Column(name = "TRIGGER_NAME")
    private String triggerName;

    @Column(name = "TRIGGER_GROUP")
    private String triggerGroup;

    @Column(name = "REPEAT_COUNT")
    private int repeatCount;

    @Column(name = "REPEAT_INTERVAL")
    private int repeatInterval;

    @Column(name = "TIMES_TRIGGERED")
    private int timesTriggered;

}
