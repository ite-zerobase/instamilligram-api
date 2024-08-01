package com.zerobase.instamilligramapi.global.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 */
    EMPTY_COMMENT_SEARCH_REQUEST(HttpStatus.BAD_REQUEST, "postId 또는 commentId는 필수값입니다.", "C4001"),
    BAD_COMMENT_SEARCH_REQUEST(HttpStatus.BAD_REQUEST, "postId와 commentId를 동시에 명시할 수 없습니다.", "C4002"),
    EMPTY_FOLLOW_REQUEST(HttpStatus.BAD_REQUEST, "팔로워와 팔로잉 대상은 비어있을 수 없습니다.", "F4001"),
    SELF_FOLLOWING_REQUEST(HttpStatus.BAD_REQUEST, "팔로워와 팔로잉 대상이 같을 수 없습니다.", "F4002"),

    /* 404 */
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글이 없습니다.", "P4041"),
    TARGET_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "대상 게시글이 없습니다.", "P4042"),
    POST_MEDIA_NOT_FOUND(HttpStatus.NOT_FOUND, "최소 1개 이상의 사진 또는 영상을 업로드해야 합니다.", "P4043"),

    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "삭제할 좋아요가 없습니다.", "L4041"),

    COMMENT_NOT_FOUND(HttpStatus.CONFLICT, "해당 댓글이 없습니다.", "C4091"),
    TARGET_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "대상 댓글이 없습니다.", "C4042"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.", "U4041"),

    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로잉 관계가 없습니다.", "F4041"),
    /* 409 */
    POST_ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요 한 게시물입니다.", "P4091"),
    COMMENT_ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요 한 댓글입니다.", "C4091"),


    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    @Override
    public String toString() {
        return this.status + " " + message + " " + code;
    }
}
