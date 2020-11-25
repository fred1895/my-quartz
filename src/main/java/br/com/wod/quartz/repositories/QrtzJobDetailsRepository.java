package br.com.wod.quartz.repositories;

import br.com.wod.quartz.entities.QrtzJobDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QrtzJobDetailsRepository extends JpaRepository<QrtzJobDetails, String> {

    List<QrtzJobDetails> findByJobGroup(String jobGroup);

    QrtzJobDetails findBySchedName(String schedName);
}
