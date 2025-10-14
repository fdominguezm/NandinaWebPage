package com.nandina.api.repositories.implementations;


import com.nandina.api.models.Post;
import com.nandina.api.models.User;
import com.nandina.api.repositories.interfaces.FeedRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@SuppressWarnings({"unchecked", "all"})
public class FeedRepositoryImpl implements FeedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    String feedQuery = """
    SELECT posts.id
    FROM posts
    WHERE
        posts.user_id IN (
            SELECT user_id FROM followers WHERE follower_user_id = :current_user_id
        )
        OR posts.user_id = :current_user_id
    ORDER BY posts.time_stamp DESC
    """;


    @Override
    public List<Post> getFeed(User user, int page, int pageSize) {
        Query nativeQuery = entityManager.createNativeQuery(feedQuery);
        nativeQuery.setParameter("current_user_id", user.getId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult((page - 1) * pageSize);

        final List<Long> idList = ((List<Long>) nativeQuery.getResultList())
                .stream()
                .toList();

        if (idList.isEmpty())
            return Collections.emptyList();

        final TypedQuery<Post> query = entityManager.createQuery("FROM Post WHERE id IN :ids ORDER BY timestamp DESC", Post.class);
        query.setParameter("ids", idList);

        return query.getResultList();
    }
}
