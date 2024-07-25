package com.zerobase.instamilligramapi.domain.posts.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostLike {
    private Integer postId;
    private String username;
    private LocalDateTime likedAt;
}