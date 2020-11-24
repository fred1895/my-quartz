package br.com.wod.quartz.repositories;

import br.com.wod.quartz.entities.QrtzCronTriggers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrtzCronTriggersRepository extends JpaRepository<QrtzCronTriggers, String> {
}
