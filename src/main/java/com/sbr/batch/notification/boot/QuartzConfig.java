package com.sbr.batch.notification.boot;

import com.sbr.batch.notification.job.EmailNotificationJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
@EnableScheduling
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob().ofType(EmailNotificationJob.class)
                .storeDurably()
                .withIdentity("Qrtz_Email_Detail")
                .withDescription("Invoke Email Notification...")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("Qrtz_Email_Detail")
                .withDescription("Invoke Email Notification")
                .withSchedule(
                        simpleSchedule().repeatForever().withIntervalInMinutes(1)
                )
                .build();
    }
}
