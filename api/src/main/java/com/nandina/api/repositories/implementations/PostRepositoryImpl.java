package com.nandina.api.repositories.implementations;



import com.nandina.api.models.*;
import com.nandina.api.repositories.interfaces.PostRepository;
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
public class PostRepositoryImpl implements PostRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Post save(Post post) {
        return entityManager.merge(post);
    }

    @Override
    public PostImage save(PostImage postImage) {
        return entityManager.merge(postImage);
    }

    @Override
    public PostComment save(PostComment postComment) {
        return entityManager.merge(postComment);
    }

    @Override
    public PostLike save(PostLike postLike) {
        return entityManager.merge(postLike);
    }

    @Override
    public void delete(PostLike postLike) {
        entityManager.createQuery("DELETE FROM PostLike WHERE post.id = :postId AND user.id = :userId")
                .setParameter("postId", postLike.getPost().getId())
                .setParameter("userId", postLike.getUser().getId())
                .executeUpdate();
    }

    @Override
    public Optional<Post> getPostById(long postId) {
        return entityManager.createQuery("FROM Post WHERE id = :id", Post.class)
                .setParameter("id", postId)
                .getResultStream().findFirst();
    }

    @Override
    public PagedContent<Long> getPostImages(Post post, int page, int pageSize) {
        String str = "SELECT COUNT(*) FROM post_images WHERE post_id = :postId";
        Query countQuery = entityManager.createNativeQuery(str);
        countQuery.setParameter("postId", post.getId());
        int count = ((Number) countQuery.getSingleResult()).intValue();

        if (count <= 0)
            return new PagedContent<>(Collections.emptyList(), page, pageSize, count);

        str = "SELECT image_id FROM post_images WHERE post_id = :postId";
        Query nativeQuery = entityManager.createNativeQuery(str);
        nativeQuery.setParameter("postId", post.getId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult((page - 1) * pageSize);

        final List<Long> idList = ((List<Long>) nativeQuery.getResultList())
                .stream()
                .toList();

        return new PagedContent<>(idList, page, pageSize, count);    }

    @Override
    public PagedContent<PostComment> getPostComments(Post post, int page, int pageSize) {
        String str = "SELECT COUNT(*) FROM post_comments WHERE post_id = :postId";
        Query countQuery = entityManager.createNativeQuery(str);
        countQuery.setParameter("postId", post.getId());
        int count = ((Number) countQuery.getSingleResult()).intValue();

        if (count <= 0)
            return new PagedContent<>(Collections.emptyList(), page, pageSize, count);

        str = "SELECT id FROM post_comments WHERE post_id = :postId ORDER BY time_stamp DESC";
        Query nativeQuery = entityManager.createNativeQuery(str);
        nativeQuery.setParameter("postId", post.getId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult((page - 1) * pageSize);

        final List<Long> idList = ((List<Long>) nativeQuery.getResultList())
                .stream()
                .toList();

        if (idList.isEmpty())
            return new PagedContent<>(Collections.emptyList(), page, pageSize, count);

        final TypedQuery<PostComment> query = entityManager.createQuery("FROM PostComment WHERE id IN :ids ORDER BY timestamp DESC", PostComment.class);
        query.setParameter("ids", idList);

        return new PagedContent<>(query.getResultList(), page, pageSize, count);
    }

    @Override
    public Long getPostLikes(Post post) {
        String str = "SELECT COUNT(*) FROM post_likes WHERE post_id = :postId";
        Query countQuery = entityManager.createNativeQuery(str);
        countQuery.setParameter("postId", post.getId());
        return ((Number) countQuery.getSingleResult()).longValue();
    }

    @Override
    public PagedContent<Post> getPostsByUser(User user, int page, int pageSize) {
        String str = "SELECT COUNT(*) FROM posts WHERE user_id = :userId";
        Query countQuery = entityManager.createNativeQuery(str);
        countQuery.setParameter("userId", user.getId());
        int count = ((Number) countQuery.getSingleResult()).intValue();

        if (count <= 0)
            return new PagedContent<>(Collections.emptyList(), page, pageSize, count);

        str = "SELECT id FROM posts WHERE user_id = :userId ORDER BY time_stamp DESC";
        Query nativeQuery = entityManager.createNativeQuery(str);
        nativeQuery.setParameter("userId", user.getId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult((page - 1) * pageSize);

        final List<Long> idList = ((List<Long>) nativeQuery.getResultList())
                .stream()
                .toList();

        if (idList.isEmpty())
            return new PagedContent<>(Collections.emptyList(), page, pageSize, count);

        final TypedQuery<Post> query = entityManager.createQuery("FROM Post WHERE id IN :ids ORDER BY timestamp DESC", Post.class);
        query.setParameter("ids", idList);

        return new PagedContent<>(query.getResultList(), page, pageSize, count);
    }

    @Override
    public PagedContent<Post> searchPosts(String query, int page, int pageSize) {
        String str = "SELECT COUNT(*) FROM posts WHERE title ILIKE '%" + query + "%' OR content ILIKE '%" + query + "%'";
        Query countQuery = entityManager.createNativeQuery(str);
        int count = ((Number) countQuery.getSingleResult()).intValue();

        if (count <= 0)
            return new PagedContent<>(Collections.emptyList(), page, pageSize, count);

        str = "SELECT id FROM posts WHERE title ILIKE '%" + query + "%' OR content ILIKE '%" + query + "%'";
        Query nativeQuery = entityManager.createNativeQuery(str);
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult((page - 1) * pageSize);

        final List<Long> idList = ((List<Long>) nativeQuery.getResultList())
                .stream()
                .toList();

        if (idList.isEmpty())
            return new PagedContent<>(Collections.emptyList(), page, pageSize, count);

        final TypedQuery<Post> typedQuery = entityManager.createQuery("FROM Post WHERE id IN :ids", Post.class)
                .setParameter("ids", idList);

        return new PagedContent<>(typedQuery.getResultList(), page, pageSize, count);
    }

    @Override
    public Optional<PostLike> findPostLike(Long postId, Long userId) {
        return entityManager.createQuery("FROM PostLike WHERE post.id = :postId AND user.id = :userId", PostLike.class)
                .setParameter("postId", postId)
                .setParameter("userId", userId)
                .getResultStream().findFirst();
    }
}

