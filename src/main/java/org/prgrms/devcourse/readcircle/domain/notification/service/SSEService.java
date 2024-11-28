package org.prgrms.devcourse.readcircle.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.domain.notification.entity.Notification;
import org.prgrms.devcourse.readcircle.domain.notification.entity.NotificationType;
import org.prgrms.devcourse.readcircle.domain.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Log4j2
public class SSEService {
    private final Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String userId){
        Long timeout = 1 * 60 * 1000L;
        SseEmitter sseEmitter = new SseEmitter(timeout);

        try{
            sseEmitterMap.put(userId, sseEmitter);
            System.out.println("SSE 연결 열림: 사용자 ID " + userId);

            sseEmitter.onCompletion(()-> sseEmitterMap.remove(userId));
            sseEmitter.onTimeout(()->{
                sseEmitterMap.remove(userId);
                sseEmitter.complete();
                System.out.println("SSE 연결 타임아웃: 사용자 ID " + userId);
            });
            sseEmitter.onError(throwable -> {
                sseEmitterMap.remove(userId);
                sseEmitter.complete();
                System.out.println("SSE 연결 오류: 사용자 ID " + userId + ", 오류: " + throwable.getMessage());
            });
        }catch (Exception e){
            sseEmitter.completeWithError(e);
            System.out.println("SSE 연결 실패 : "+ e.getMessage());
        }

        return sseEmitter;
    }

    public void sendNotification(String userId, String message, NotificationType type, Long notificationId){
        SseEmitter sseEmitter = sseEmitterMap.get(userId);
        if(sseEmitter != null){
            try{
                sseEmitter.send(
                        SseEmitter.event()
                                .id(notificationId.toString())
                                .name(type.toString())
                                .data(message)
                );

                System.out.println("알림 전송 성공: 사용자 ID " + userId);
            }catch (IOException e){
                sseEmitterMap.remove(userId);
                System.out.println("알림 전송 실패, SSE 연결 종료: 사용자 ID " + userId);
            }
        }
    }
}
