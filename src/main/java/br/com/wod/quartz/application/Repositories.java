package br.com.wod.quartz.application;

import br.com.wod.quartz.infrastructure.repositories.JobsDetailsRepositoryImpl;
import br.com.wod.quartz.infrastructure.repositories.TriggersRepositoryImpl;
import br.com.wod.quartz.infrastructure.repositories.JobsDetailsRepository;
import br.com.wod.quartz.infrastructure.repositories.TriggersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Repositories {

    @Bean
    public JobsDetailsRepository jobsDetailsRepository() {
        return new JobsDetailsRepositoryImpl();
    }

    @Bean
    public TriggersRepository triggersRepository() {
        return new TriggersRepositoryImpl();
    }
}
