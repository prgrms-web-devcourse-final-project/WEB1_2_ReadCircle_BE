package org.prgrms.devcourse.readcircle.common.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.common.notification.dto.MessageDTO;
import org.prgrms.devcourse.readcircle.common.notification.entity.Notification;
import org.prgrms.devcourse.readcircle.common.notification.entity.NotificationType;
import org.prgrms.devcourse.readcircle.common.notification.exception.NotificationException;
import org.prgrms.devcourse.readcircle.common.notification.repository.NotificationRepository;
import org.prgrms.devcourse.readcircle.common.notification.dto.NotificationDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SseService sseService;

    public Notification saveNotification(String toUserId, String message, NotificationType type){
        MessageDTO messageDTO = new MessageDTO(message);
        try{
            Notification notification = Notification.builder()
                    .toUserId(toUserId)
                    .message(message)
                    .type(type)
                    .build();

            Notification noti = notificationRepository.save(notification);
            sseService.makeNotification(toUserId, noti, messageDTO );

            return notification;
        }catch(Exception e){
            log.info(e.getMessage());
            throw NotificationException.NOT_REGISTERED_NOTIFICATION_EXCEPTION.getTaskException();
        }
    }

    public List<Notification> getList(String userId){
        List<Notification> notifications = notificationRepository.findByToUserId(userId);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for(int i=0; i<notifications.size(); i++){
            Notification noti = notifications.get(i);
            if(!noti.isRead()) noti.markAsRead();

        }
        return notifications;
    }

    @Transactional
    public void deleteNotifications(){
        notificationRepository.deleteNotifications();
    }
}
