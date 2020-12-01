package br.com.wod.quartz.infrastructure.repositories;

import br.com.wod.quartz.core.entities.QrtzJobDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobsDetailsRepository {

    QrtzJobDetails findByNameAndGroup(String jobName, String jobGroup);

    List<QrtzJobDetails> findByGroup(String group);

    QrtzJobDetails findBySchedName(String schedName);
}
