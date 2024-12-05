package org.prgrms.devcourse.readcircle.common.notification.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.common.notification.entity.Notification;
import org.prgrms.devcourse.readcircle.common.notification.entity.NotificationType;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long notificationId;
    private String toUserId;
    private NotificationType type;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;

    public NotificationDTO(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.toUserId = notification.getToUserId();
        this.type = notification.getType();
        this.message = notification.getMessage();
        this.isRead = notification.isRead();
        this.createdAt = notification.getCreatedAt();
    }
}
