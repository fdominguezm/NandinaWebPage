package com.nandina.api.controllers;


import com.nandina.api.dtos.PostDTO;
import com.nandina.api.forms.PageForm;
import com.nandina.api.models.Post;
import com.nandina.api.models.User;
import com.nandina.api.services.interfaces.FeedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getFeed(@Valid @ModelAttribute PageForm pageForm) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> posts = feedService.getFeed(user, pageForm.getPageOrDefault(), pageForm.getSizeOrDefault());
        List<PostDTO> postDTOs = posts.stream().map(PostDTO::fromPost).toList();
        return ResponseEntity.ok(postDTOs);
    }

}
