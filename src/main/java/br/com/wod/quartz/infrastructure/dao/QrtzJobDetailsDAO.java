package br.com.wod.quartz.infrastructure.dao;

import br.com.wod.quartz.core.entities.QrtzJobDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QrtzJobDetailsDAO extends JpaRepository<QrtzJobDetails, String> {

    QrtzJobDetails findByJobNameAndJobGroup(String jobName, String jobGroup);

    List<QrtzJobDetails> findByJobGroup(String jobGroup);

    QrtzJobDetails findBySchedName(String schedName);
}
