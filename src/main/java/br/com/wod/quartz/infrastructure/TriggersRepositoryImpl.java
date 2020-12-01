package br.com.wod.quartz.infrastructure;

import br.com.wod.quartz.core.entities.QrtzTriggers;
import br.com.wod.quartz.infrastructure.dao.QrtzTriggersDAO;
import br.com.wod.quartz.infrastructure.repositories.TriggersRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TriggersRepositoryImpl implements TriggersRepository {
    @Autowired
    private QrtzTriggersDAO dao;

    @Override
    public QrtzTriggers findByJobName(String jobName) {
        return dao.findByJobName(jobName);
    }

    @Override
    public QrtzTriggers findBySchedName(String schedName) {
        return dao.findBySchedName(schedName);
    }
}
