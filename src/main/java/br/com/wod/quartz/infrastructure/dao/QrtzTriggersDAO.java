package br.com.wod.quartz.infrastructure.dao;

import br.com.wod.quartz.core.entities.QrtzTriggers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QrtzTriggersDAO extends JpaRepository<QrtzTriggers, String> {

    QrtzTriggers findByJobName(String jobName);

    QrtzTriggers findBySchedName(String schedName);

    List<QrtzTriggers> findByTriggerState(String triggerState);

}
