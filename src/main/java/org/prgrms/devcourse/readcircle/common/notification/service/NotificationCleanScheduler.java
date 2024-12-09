package org.prgrms.devcourse.readcircle.common.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationCleanScheduler {
    private final NotificationService notificationService;

    //매일 오전 1시에 알림 삭제
    //test -> 1분마다 삭제       @Scheduled(cron = "0 0/1 * * * ?")
    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanNotification(){
        notificationService.deleteNotifications();
        System.out.println("notification deleted successfully");
    }
}
