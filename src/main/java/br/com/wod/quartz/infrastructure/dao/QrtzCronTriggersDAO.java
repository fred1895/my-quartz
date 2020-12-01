package br.com.wod.quartz.infrastructure.dao;

import br.com.wod.quartz.core.entities.QrtzCronTriggers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrtzCronTriggersDAO extends JpaRepository<QrtzCronTriggers, String> {
}
