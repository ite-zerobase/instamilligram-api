package com.zerobase.instamilligramapi.domain.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentMeta {
    private String username;
    private Integer commentId;
    private Integer sign;
}
