package com.nandina.api.services.implementations;

import com.nandina.api.exceptions.ForbiddenException;
import com.nandina.api.exceptions.specifics.NotificationNotFoundException;
import com.nandina.api.models.PagedContent;
import com.nandina.api.models.User;
import com.nandina.api.models.notifications.Notification;
import com.nandina.api.models.notifications.NotificationType;
import com.nandina.api.repositories.interfaces.NotificationRepository;
import com.nandina.api.services.interfaces.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationPushService notificationPushService;
    private final NotificationRepository notificationRepository;


    @Override
    public PagedContent<Notification> getUnreadNotifications(User user, int page, int pageSize) {
        return notificationRepository.getUnreadNotifications(user, page, pageSize);
    }

    @Override
    public void readNotification(User user, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new NotificationNotFoundException(notificationId.toString()));
        if (!notification.getUserId().equals(user.getId())) {
            throw new ForbiddenException(notificationId.toString());
        }
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}

