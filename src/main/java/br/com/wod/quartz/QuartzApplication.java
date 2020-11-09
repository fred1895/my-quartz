package br.com.wod.quartz;

import br.com.wod.quartz.service.SecondJobService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuartzApplication implements CommandLineRunner {

	@Autowired
	private SecondJobService secondJobService;

	public static void main(String[] args){
		SpringApplication.run(QuartzApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		secondJobService.startSecondJob();
	}
}
