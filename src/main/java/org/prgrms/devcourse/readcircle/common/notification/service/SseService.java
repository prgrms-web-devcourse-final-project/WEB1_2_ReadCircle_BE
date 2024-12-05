package org.prgrms.devcourse.readcircle.common.notification.service;

import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.common.notification.dto.MessageDTO;
import org.prgrms.devcourse.readcircle.common.notification.entity.Notification;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j2
public class SseService {
    private final Map<String, SseEmitter> emittersMap = new ConcurrentHashMap<>();
    private static final Long DEFAULT_TIMEOUT = 60 * 1000L;     //10초

    //sse 연결
    public SseEmitter connect(String userId){
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emittersMap.put(userId,emitter);

        try{
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected!"));
        }catch (IOException e){
            log.info(e.getMessage());
        }

        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            this.emittersMap.remove(userId);
        });

        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitter.complete();
        });

        return emitter;
    }

    //sse 생성
    public void makeNotification(String userId, Notification notification, MessageDTO data){
        SseEmitter emitter = emittersMap.get(userId);
        try{
            emitter.send(SseEmitter.event()
                            .id(notification.getNotificationId().toString())
                            .name(notification.getType().toString())
                            .data(data)
                    );
        } catch (Exception e){
            log.info(e.getMessage());
        }
    }
}
