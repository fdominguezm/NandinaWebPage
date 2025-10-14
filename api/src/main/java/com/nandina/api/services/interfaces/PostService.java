package com.nandina.api.services.interfaces;


import com.nandina.api.models.PagedContent;
import com.nandina.api.models.Post;
import com.nandina.api.models.PostComment;
import com.nandina.api.models.User;

import java.util.Optional;

public interface PostService {

    // Modifiers
    Post createPost( User user, String title, String content);
    void saveImage(Post post, byte[] image);
    PostComment addComment(Post post, User user, String content);
    void likePost(Post post, User user);
    void unlikePost(Post post, User user);


    // Getters
    Optional<Post> getPostById(long postId);
    PagedContent<Long> getPostImages(Post post, int page, int pageSize);
    PagedContent<PostComment> getPostComments(Post post, int page, int pageSize);
    Long getPostLikes(Post post);
    PagedContent<Post> getPostsByUser(User user, int page, int pageSize);

    PagedContent<Post> searchPosts(String query, int page, int pageSize);

    // Checkers
    boolean isPostLiked(Long postId, Long userId);
}

