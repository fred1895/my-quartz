package br.com.wod.quartz.infrastructure.repositories;

import br.com.wod.quartz.core.entities.QrtzTriggers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TriggersRepository {
    QrtzTriggers findByJobName(String jobName);

    QrtzTriggers findBySchedName(String schedName);

}
