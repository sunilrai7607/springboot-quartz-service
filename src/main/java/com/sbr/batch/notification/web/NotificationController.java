package com.sbr.batch.notification.web;

import com.sbr.batch.notification.domain.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/api/v1")
public class NotificationController {

    @PostMapping(path = "/notifications/{id}")
    public void setupNotification(@PathVariable("id") final String id,
                                  @RequestParam("type") final String type,
                                  @RequestBody NotificationRequest notificationRequest){

    }
}
