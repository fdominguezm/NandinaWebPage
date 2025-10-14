package com.nandina.api.services.implementations;



import com.nandina.api.models.*;
import com.nandina.api.repositories.interfaces.PostRepository;
import com.nandina.api.services.interfaces.ImageService;
import com.nandina.api.services.interfaces.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageService imageService;

    @Override
    public Post createPost(User user, String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        post.setTimestamp(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public void saveImage(Post post, byte[] image) {
        Image aux = imageService.create(image);
        PostImage postImage = new PostImage();
        postImage.setPost(post);
        postImage.setImage(aux);
        postRepository.save(postImage);
    }

    @Override
    public PostComment addComment(Post post, User user, String content) {
        PostComment comment = new PostComment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);
        comment.setTimestamp(LocalDateTime.now());
        return postRepository.save(comment);
    }

    @Override
    public void likePost(Post post, User user) {
        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setUser(user);
        postRepository.save(postLike);
    }

    @Override
    public void unlikePost(Post post, User user) {
        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setUser(user);
        postRepository.delete(postLike);
    }

    @Override
    public Optional<Post> getPostById(long postId) {
        return postRepository.getPostById(postId);
    }

    @Override
    public PagedContent<Long> getPostImages(Post post, int page, int pageSize) {
        return postRepository.getPostImages(post, page, pageSize);
    }

    @Override
    public PagedContent<PostComment> getPostComments(Post post, int page, int pageSize) {
        return postRepository.getPostComments(post, page, pageSize);
    }

    @Override
    public Long getPostLikes(Post post) {
        return postRepository.getPostLikes(post);
    }


    @Override
    public PagedContent<Post> getPostsByUser(User user, int page, int pageSize) {
        return postRepository.getPostsByUser(user, page, pageSize);
    }

    @Override
    public PagedContent<Post> searchPosts(String query, int page, int pageSize) {
        return postRepository.searchPosts(query, page, pageSize);
    }


    @Override
    public boolean isPostLiked(Long postId, Long userId) {
        return postRepository.findPostLike(postId, userId).isPresent();
    }
}

