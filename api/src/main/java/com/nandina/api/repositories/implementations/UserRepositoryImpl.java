package com.nandina.api.repositories.implementations;



import com.nandina.api.models.PagedContent;
import com.nandina.api.models.User;
import com.nandina.api.repositories.interfaces.UserRepository;
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
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(User user) {
        return entityManager.merge(user);
    }


    @Override
    public Optional<User> getUserByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultStream().findFirst();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.id = :userId", User.class)
                .setParameter("userId", userId)
                .getResultStream().findFirst();
    }

    @Override
    public PagedContent<User> searchUsersByName(User user, String name, int page, int pageSize) {
        String str = "SELECT COUNT(*) FROM users WHERE name ILIKE '%" + name + "%' AND id != :userId";
        Query countQuery = entityManager.createNativeQuery(str);
        countQuery.setParameter("userId", user.getId());
        int count = ((Number) countQuery.getSingleResult()).intValue();

        if (count <= 0)
            return new PagedContent<>(Collections.emptyList(), page, pageSize, count);

        str = "SELECT id FROM users WHERE name ILIKE '%" + name + "%' AND id != :userId";
        Query nativeQuery = entityManager.createNativeQuery(str);
        nativeQuery.setParameter("userId", user.getId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult((page - 1) * pageSize);

        final List<Long> idList = ((List<Long>) nativeQuery.getResultList())
                .stream()
                .toList();

        if (idList.isEmpty())
            return new PagedContent<>(Collections.emptyList(), page, pageSize, count);

        final TypedQuery<User> query = entityManager.createQuery("FROM User WHERE id IN :ids", User.class)
                .setParameter("ids", idList);

        return new PagedContent<>(query.getResultList(), page, pageSize, count);
    }

    @Override
    public boolean emailExists(String email) {
        Long count = entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

}

