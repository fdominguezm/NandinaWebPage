package com.nandina.api.controllers;

import com.nandina.api.exceptions.specifics.UserNotFoundException;
import com.nandina.api.forms.PageForm;
import com.nandina.api.models.*;
import com.nandina.api.dtos.PostCommentDTO;
import com.nandina.api.dtos.PostDTO;
import com.nandina.api.exceptions.BadRequest;
import com.nandina.api.exceptions.ForbiddenException;
import com.nandina.api.exceptions.specifics.PostNotFoundException;
import com.nandina.api.forms.PostCommentForm;
import com.nandina.api.forms.PostForm;
import com.nandina.api.services.interfaces.PostService;
import com.nandina.api.services.interfaces.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid PostForm postForm) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            Post post = postService.createPost(user, postForm.getTitle(), postForm.getContent());
            return ResponseEntity.ok(PostDTO.fromPost(post));
        }
        throw new ForbiddenException("User is null");
    }

    @PostMapping("/{postId}/images")
    public ResponseEntity<?> createPostImage(@PathVariable("postId") Long postId,
                                             @RequestParam("image") MultipartFile image) throws IOException {
        Post post = postService.getPostById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!post.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(postId.toString());
        }
        if (image.isEmpty()) {
            throw new BadRequest("Image is empty");
        }
        postService.saveImage(post, image.getBytes());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<PostCommentDTO> createPostComment(@PathVariable("postId") Long postId,
                                                            @RequestBody @Valid PostCommentForm commentForm) {
        Post post = postService.getPostById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostComment comment = postService.addComment(post, user, commentForm.getContent());
        return ResponseEntity.ok(PostCommentDTO.fromComment(comment));
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<?> likePost(@PathVariable("postId") Long postId) {
        Post post = postService.getPostById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postService.likePost(post, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<?> unlikePost(@PathVariable("postId") Long postId) {
        Post post = postService.getPostById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postService.unlikePost(post, user);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("postId") Long postId) {
        Post post = postService.getPostById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return ResponseEntity.ok(PostDTO.fromPost(post));
    }

    @GetMapping("/{postId}/images")
    public ResponseEntity<PagedContent<Long>> getPostImages(@PathVariable("postId") Long postId,
                                           @Valid @ModelAttribute PageForm pageForm) {
        Post post = postService.getPostById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        PagedContent<Long> ids = postService.getPostImages(post, pageForm.getPageOrDefault(), pageForm.getSizeOrDefault());
        return ResponseEntity.ok(ids);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<PagedContent<PostCommentDTO>> getPostComments(@PathVariable("postId") Long postId,
                                             @Valid @ModelAttribute PageForm pageForm) {
        Post post = postService.getPostById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        PagedContent<PostComment> comments = postService.getPostComments(post, pageForm.getPageOrDefault(), pageForm.getSizeOrDefault());
        List<PostCommentDTO> commentDTOs = comments.getContent().stream().map(PostCommentDTO::fromComment).toList();
        PagedContent<PostCommentDTO> toReturn = new PagedContent<>(commentDTOs, comments.getCurrentPage(), comments.getPageSize(), comments.getTotalCount());
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<Long> getPostLikes(@PathVariable("postId") Long postId) {
        Post post = postService.getPostById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return ResponseEntity.ok(postService.getPostLikes(post));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPostsByUser(@PathVariable("userId") Long userId, @Valid @ModelAttribute PageForm pageForm){
        Optional<User> auxUser = userService.getUserById(userId);

        if (auxUser.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        
        User user = auxUser.get();
        PagedContent<Post> posts = postService.getPostsByUser(user, pageForm.getPageOrDefault(), pageForm.getSizeOrDefault());
        List<PostDTO> postDTOs = posts.getContent().stream().map(PostDTO::fromPost).toList();
        PagedContent<PostDTO> toReturn = new PagedContent<>(postDTOs, posts.getCurrentPage(), posts.getPageSize(), posts.getTotalCount());

        return ResponseEntity.ok(toReturn);

    }

    @GetMapping("/likes/{postId}")
    public ResponseEntity<?> isLiked (@PathVariable("postId") @Min(1) Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(postService.isPostLiked(postId, user.getId()));
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> searchUsersByName(@RequestParam(name = "query") String query,
                                               @Valid @ModelAttribute PageForm pageForm) {
        PagedContent<Post> posts = postService.searchPosts(query, pageForm.getPageOrDefault(), pageForm.getSizeOrDefault());
        List<PostDTO> postDTOs = posts.getContent().stream().map(PostDTO::fromPost).toList();
        PagedContent<PostDTO> toReturn = new PagedContent<>(postDTOs, posts.getCurrentPage(), posts.getPageSize(), posts.getTotalCount());
        return ResponseEntity.ok(toReturn);
    }


}

