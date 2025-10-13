package com.nandina.api.repositories.interfaces;



import com.nandina.api.models.PagedContent;
import com.nandina.api.models.User;
import com.nandina.api.models.notifications.Notification;

import java.util.Optional;

public interface NotificationRepository {
    Optional<Notification> findById(Long id);
    Notification save(Notification notification);
    PagedContent<Notification> getUnreadNotifications(User user, int page, int pageSize);
}

