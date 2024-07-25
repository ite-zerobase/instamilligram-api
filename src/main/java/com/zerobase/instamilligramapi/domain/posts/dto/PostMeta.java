package com.zerobase.instamilligramapi.domain.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMeta {
    private String username;
    private Integer postId;
    private Integer commentId;
    private Integer sign;
}
