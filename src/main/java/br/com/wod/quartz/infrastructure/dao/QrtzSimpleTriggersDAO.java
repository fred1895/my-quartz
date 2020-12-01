package br.com.wod.quartz.infrastructure.dao;

import br.com.wod.quartz.core.entities.QrtzSimpleTriggers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrtzSimpleTriggersDAO extends JpaRepository<QrtzSimpleTriggers, String> {
}
