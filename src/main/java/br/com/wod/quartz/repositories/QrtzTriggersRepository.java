package br.com.wod.quartz.repositories;

import br.com.wod.quartz.entities.QrtzTriggers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QrtzTriggersRepository extends JpaRepository<QrtzTriggers, String> {
    QrtzTriggers findByJobName(String jobName);

    QrtzTriggers findBySchedName(String schedName);

    List<QrtzTriggers> findByTriggerState(String triggerState);

}
