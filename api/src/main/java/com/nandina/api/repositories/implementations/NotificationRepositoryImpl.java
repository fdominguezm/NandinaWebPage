package com.nandina.api.repositories.implementations;


import com.nandina.api.models.PagedContent;
import com.nandina.api.models.User;
import com.nandina.api.models.notifications.Notification;
import com.nandina.api.repositories.interfaces.NotificationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings({"unchecked", "all"})
public class NotificationRepositoryImpl implements NotificationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Notification> findById(Long id) {
        return entityManager.createQuery("FROM Notification WHERE id = :id", Notification.class)
                .setParameter("id", id)
                .getResultStream().findFirst();
    }

    @Override
    public Notification save(Notification notification) {
        return entityManager.merge(notification);
    }

    @Override
    public PagedContent<Notification> getUnreadNotifications(User user, int page, int pageSize) {
        String str = "SELECT COUNT(*) FROM notifications WHERE user_id = :userId AND read = false";
        Query countQuery = entityManager.createNativeQuery(str);
        countQuery.setParameter("userId", user.getId());
        int count = ((Number) countQuery.getSingleResult()).intValue();

        if (count <= 0)
            return new PagedContent<>(Collections.emptyList(), page, pageSize, count);

        str = "SELECT id FROM notifications WHERE user_id = :userId AND read = false";
        Query nativeQuery = entityManager.createNativeQuery(str);
        nativeQuery.setParameter("userId", user.getId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult((page - 1) * pageSize);

        final List<Long> idList = ((List<Long>) nativeQuery.getResultList())
                .stream()
                .toList();

        if (idList.isEmpty())
            return new PagedContent<>(Collections.emptyList(), page, pageSize, count);

        final TypedQuery<Notification> query = entityManager.createQuery("FROM Notification WHERE id IN :ids", Notification.class)
                .setParameter("ids", idList);

        return new PagedContent<>(query.getResultList(), page, pageSize, count);
    }
}

