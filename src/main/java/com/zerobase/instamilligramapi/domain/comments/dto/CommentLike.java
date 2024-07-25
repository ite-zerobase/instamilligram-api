package com.zerobase.instamilligramapi.domain.comments.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentLike {
    private Integer commentId;
    private String username;
    private LocalDateTime likedAt;
}
