package br.com.wod.quartz;

import br.com.wod.quartz.jobs.MyJob;
import br.com.wod.quartz.trigger.Schedule;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuartzApplication implements CommandLineRunner {

	@Autowired
	private Scheduler scheduler;

	public static void main(String[] args){
		SpringApplication.run(QuartzApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		scheduler.start();

		JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("FirstJob", "group1").build();
		Trigger trigger = Schedule.simpleSchedule();
		scheduler.scheduleJob(jobDetail, trigger);
	}
}
