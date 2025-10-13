package com.nandina.api.dtos;

import com.nandina.api.models.notifications.Notification;
import com.nandina.api.models.notifications.NotificationType;

public record NotificationDto(Long id, String content, NotificationType type, Boolean read) {
    public static NotificationDto from(Notification notification) {
        return new NotificationDto(
                notification.getId(),
                notification.getContent(),
                notification.getType(),
                notification.getRead());
    }
}
