package com.sbr.batch.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailNotificationServiceImpl implements EmailNotificationService{
    @Override
    public void emailReminder() {
        log.info("Email Reminder trigger by service");
    }
}
