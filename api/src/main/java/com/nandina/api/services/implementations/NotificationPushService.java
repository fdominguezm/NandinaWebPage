package com.nandina.api.services.implementations;


import com.nandina.api.dtos.NotificationDto;
import com.nandina.api.models.notifications.Notification;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationPushService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // better timeout

        emitter.onCompletion(() -> removeEmitter(userId));
        emitter.onTimeout(() -> {
            removeEmitter(userId);
        });
        emitter.onError(e -> {
            removeEmitter(userId);
        });

        try {
            emitter.send(SseEmitter.event()
                    .name("new-connection")
                    .data(""));
            emitters.put(userId, emitter);
            return emitter;
        } catch (IOException e) {
            return null;
        }

    }

    private void removeEmitter(Long userId) {
        emitters.remove(userId);
    }

    public void sendMessage(Notification notification) {
        SseEmitter emitter = emitters.get(notification.getUserId());
        if (emitter != null) {
            try {
                NotificationDto dto = new NotificationDto(
                        notification.getId(),
                        notification.getContent(),
                        notification.getType(),
                        notification.getRead());
                emitter.send(SseEmitter.event()
                        .name("new-notification")
                        .data(dto));
            } catch (IOException e) {
                removeEmitter(notification.getUserId());
            }
        }
    }
}


