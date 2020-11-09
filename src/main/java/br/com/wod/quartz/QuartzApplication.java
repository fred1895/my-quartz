package br.com.wod.quartz;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuartzApplication {

	@Autowired
	private Scheduler scheduler;

	public static void main(String[] args){
		SpringApplication.run(QuartzApplication.class, args);
	}

}
