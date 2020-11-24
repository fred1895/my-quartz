package br.com.wod.quartz.repositories;

import br.com.wod.quartz.entities.QrtzJobDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrtzJobDetailsRepository extends JpaRepository<QrtzJobDetails, String> {

}
