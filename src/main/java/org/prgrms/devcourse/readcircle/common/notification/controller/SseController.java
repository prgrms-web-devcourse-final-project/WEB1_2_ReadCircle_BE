package org.prgrms.devcourse.readcircle.common.notify.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.notification.dto.NotificationDTO;
import org.prgrms.devcourse.readcircle.common.notification.entity.Notification;
import org.prgrms.devcourse.readcircle.common.notification.repository.NotificationRepository;
import org.prgrms.devcourse.readcircle.common.notification.service.NotificationService;
import org.prgrms.devcourse.readcircle.common.notification.service.SseService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class SseController {
    private final SseService sseService;
    private final NotificationService notificationService;

    //sse 연결
    @GetMapping(value = "/sub",  produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(Authentication authentication){
        return sseService.connect(authentication.getName());
    }

    //저장된 내 알림 조회
    @GetMapping("/list")
    public List<Notification> getNotifications(Authentication authentication){
        List<Notification> notifications = notificationService.getList(authentication.getName());
        return notifications;
    }
}
