package com.nandina.api.repositories.interfaces;


import com.nandina.api.models.*;

import java.util.Optional;

public interface PostRepository{

    // Modifiers
    Post save(Post post);
    PostImage save(PostImage postImage);
    PostComment save(PostComment postComment);
    PostLike save(PostLike postLike);
    void delete(PostLike postLike);

    // Getters
    Optional<Post> getPostById(long postId);
    PagedContent<Long> getPostImages(Post post, int page, int pageSize);
    PagedContent<PostComment> getPostComments(Post post, int page, int pageSize);
    Long getPostLikes(Post post);
    PagedContent<Post> getPostsByUser(User user, int page, int pageSize);
    PagedContent<Post> searchPosts(String query, int page, int pageSize);
    Optional<PostLike> findPostLike(Long postId, Long userId);
}

