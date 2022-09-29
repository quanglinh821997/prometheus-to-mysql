package com.microtech;

import com.microtech.jobs.MyJobs;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;



public class PrometheusDemoApplication {

	public static void main(String[] args) throws SchedulerException {
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerName", "prometheus")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(2).repeatForever()).build();
		Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("triggerName2", "prometheus")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(2).repeatForever()).build();

		JobDetail goGC = JobBuilder.newJob(MyJobs.class)
				.withIdentity("go_gc_duration_seconds", "prometheus")
				.usingJobData("metric", "go_gc_duration_seconds")
				.build();
		JobDetail job = JobBuilder.newJob(MyJobs.class)
				.withIdentity("prometheus_http_requests_total", "prometheus")
				.usingJobData("metric", "prometheus_http_requests_total")
				.build();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(goGC, trigger);
		scheduler.scheduleJob(job, trigger2);
	}
}
