package com.nandina.api.services.interfaces;


import com.nandina.api.models.PagedContent;
import com.nandina.api.models.User;
import com.nandina.api.models.notifications.Notification;

public interface NotificationService {

    PagedContent<Notification> getUnreadNotifications(User user, int page, int pageSize);
    void readNotification(User user, Long notificationId);
}

