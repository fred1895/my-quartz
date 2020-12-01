package br.com.wod.quartz.infrastructure;

import br.com.wod.quartz.core.entities.QrtzJobDetails;
import br.com.wod.quartz.infrastructure.dao.QrtzJobDetailsDAO;
import br.com.wod.quartz.infrastructure.repositories.JobsDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JobsDetailsRepositoryImpl implements JobsDetailsRepository {
    @Autowired
    private QrtzJobDetailsDAO dao;

    @Override
    public QrtzJobDetails findByNameAndGroup(String jobName, String jobGroup) {
        return dao.findByJobNameAndJobGroup(jobName, jobGroup);
    }

    @Override
    public List<QrtzJobDetails> findByGroup(String group) {
        return dao.findByJobGroup(group);
    }

    @Override
    public QrtzJobDetails findBySchedName(String schedName) {
        return dao.findBySchedName(schedName);
    }
}
