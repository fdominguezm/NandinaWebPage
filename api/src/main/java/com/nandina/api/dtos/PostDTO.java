package com.nandina.api.dtos;

import com.nandina.api.models.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime timestamp;

    public static PostDTO fromPost(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.id = post.getId();
        postDTO.userId = post.getUser().getId();
        postDTO.title = post.getTitle();
        postDTO.content = post.getContent();
        postDTO.timestamp = post.getTimestamp();
        return postDTO;
    }

}
