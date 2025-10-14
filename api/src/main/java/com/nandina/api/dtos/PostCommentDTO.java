package com.nandina.api.dtos;

import com.nandina.api.models.PostComment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostCommentDTO {
    private Long id;
    private Long postId;
    private UserDTO user;
    private String content;
    private LocalDateTime timestamp;

    public static PostCommentDTO fromComment(PostComment comment) {
        PostCommentDTO dto = new PostCommentDTO();
        dto.id = comment.getId();
        dto.postId = comment.getPost().getId();
        dto.user = UserDTO.fromUser(comment.getUser());
        dto.content = comment.getContent();
        dto.timestamp = comment.getTimestamp();
        return dto;
    }
}
