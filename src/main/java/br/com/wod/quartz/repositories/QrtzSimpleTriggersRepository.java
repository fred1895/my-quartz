package br.com.wod.quartz.repositories;

import br.com.wod.quartz.entities.QrtzSimpleTriggers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrtzSimpleTriggersRepository extends JpaRepository<QrtzSimpleTriggers, String> {
}
