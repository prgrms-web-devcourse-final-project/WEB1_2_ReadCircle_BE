package org.prgrms.devcourse.readcircle.common.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.common.notification.entity.Notification;
import org.prgrms.devcourse.readcircle.common.notification.entity.NotificationType;
import org.prgrms.devcourse.readcircle.common.notification.exception.NotificationException;
import org.prgrms.devcourse.readcircle.common.notification.repository.NotificationRepository;
import org.prgrms.devcourse.readcircle.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Override
    public Notification saveNotification(String userId, String message, NotificationType type){
        userService.findUserByUserId(userId);
        try{
            Notification notification = Notification.builder()
                    .userId(userId)
                    .message(message)
                    .type(type)
                    .build();

            notificationRepository.save(notification);
            return notification;
        }catch(Exception e){
            log.info(e.getMessage());
            throw NotificationException.NOT_REGISTERED_NOTIFICATION_EXCEPTION.getTaskException();
        }
    }

    @Override
    public String findUser(String userId){
        userService.findUserByUserId(userId);
        return userId;
    }
}
