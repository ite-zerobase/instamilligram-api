package com.zerobase.instamilligramapi.domain.comments.dto;

import lombok.Data;

@Data
public class CommentSearch {
    private Integer postId;
    private Integer parentId;
}
