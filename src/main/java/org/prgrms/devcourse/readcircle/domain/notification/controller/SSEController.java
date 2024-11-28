package org.prgrms.devcourse.readcircle.domain.notification.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.notification.dto.SseRequestDTO;
import org.prgrms.devcourse.readcircle.domain.notification.service.NotificationServiceImpl;
import org.prgrms.devcourse.readcircle.domain.notification.service.SSEService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class SSEController {
    private final SSEService sseService;
    private final NotificationServiceImpl notificationService;

    @GetMapping( value = "/subscribe/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@PathVariable String userId){
        notificationService.findUser(userId);
        return sseService.subscribe(userId);
    }
}
