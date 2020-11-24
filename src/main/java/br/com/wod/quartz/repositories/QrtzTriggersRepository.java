package br.com.wod.quartz.repositories;

import br.com.wod.quartz.entities.QrtzTriggers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrtzTriggersRepository extends JpaRepository<QrtzTriggers, String> {
}
