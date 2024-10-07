package com.zerobase.instamilligramapi.domain.comments.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentSearch {
    private Integer postId;
    private Integer commentId;
    @NotNull(message = "requestingUser은 null일 수 없습니다.")
    private String requestingUser;

    public static CommentSearch fromCommentId(int commentId, String requestingUser) {
        CommentSearch commentSearch = new CommentSearch();
        commentSearch.setCommentId(commentId);
        commentSearch.setRequestingUser(requestingUser);
        return commentSearch;
    }
    public static CommentSearch fromPostId(int postId, String requestingUser) {
        CommentSearch commentSearch = new CommentSearch();
        commentSearch.setPostId(postId);
        commentSearch.setRequestingUser(requestingUser);
        return commentSearch;
    }
}
