package com.nandina.api.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentForm {

    @NotBlank
    private String content;
}
