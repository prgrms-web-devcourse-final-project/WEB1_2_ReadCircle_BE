package org.prgrms.devcourse.readcircle.domain.notification.service;

import org.prgrms.devcourse.readcircle.domain.notification.entity.Notification;
import org.prgrms.devcourse.readcircle.domain.notification.entity.NotificationType;

public interface NotificationService {
    Notification saveNotification(String userId, String message, NotificationType type);
    String findUser(String userId);
}
