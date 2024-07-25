package com.zerobase.instamilligramapi.domain.comments.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentSearch {
    private Integer postId;
    private Integer commentId;
    @NotNull(message = "currentUsername은 null일 수 없습니다.")
    private String currentUsername;

    public static CommentSearch fromCommentId(int commentId, String currentUsername) {
        CommentSearch commentSearch = new CommentSearch();
        commentSearch.setCommentId(commentId);
        commentSearch.setCurrentUsername(currentUsername);
        return commentSearch;
    }
    public static CommentSearch fromPostId(int postId, String currentUsername) {
        CommentSearch commentSearch = new CommentSearch();
        commentSearch.setPostId(postId);
        commentSearch.setCurrentUsername(currentUsername);
        return commentSearch;
    }
}
