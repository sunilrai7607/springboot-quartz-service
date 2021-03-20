package com.sbr.batch.notification.job;

import com.sbr.batch.notification.service.EmailNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * EmailNotification Job
 */
@Component
@Slf4j
public class EmailNotificationJob implements Job {

    private final EmailNotificationService emailNotificationService;

    @Autowired
    public EmailNotificationJob(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Job ** {} ** fired @ {}", jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getFireTime());
        emailNotificationService.emailReminder();
        log.info("Next job scheduled @ {}", jobExecutionContext.getNextFireTime());
    }
}
