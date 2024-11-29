package org.prgrms.devcourse.readcircle.common.notification.service;

import org.prgrms.devcourse.readcircle.common.notification.entity.NotificationType;
import org.prgrms.devcourse.readcircle.common.notification.entity.Notification;

public interface NotificationService {
    Notification saveNotification(String userId, String message, NotificationType type);
    String findUser(String userId);
}
