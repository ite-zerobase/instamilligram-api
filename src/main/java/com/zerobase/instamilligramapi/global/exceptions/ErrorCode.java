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
    EMPTY_IMAGE_POST(HttpStatus.BAD_REQUEST, "이미지를 업로드 해 주세요.", "P4001"),

    /* 401 */
    TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.", "C4011"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.", "C4012"),
    TOKEN_NULL(HttpStatus.UNAUTHORIZED, "토큰이 없습니다.", "C4013"),
    WRONG_USERNAME_OR_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 사용자명 또는 비밀번호입니다.", "L4011"),

    /* 404 */
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글이 없습니다.", "P4041"),
    TARGET_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "대상 게시글이 없습니다.", "P4042"),
    POST_MEDIA_NOT_FOUND(HttpStatus.NOT_FOUND, "최소 1개 이상의 사진 또는 영상을 업로드해야 합니다.", "P4043"),

    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "삭제할 좋아요가 없습니다.", "L4041"),

    COMMENT_NOT_FOUND(HttpStatus.CONFLICT, "해당 댓글이 없습니다.", "C4041"),
    TARGET_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "대상 댓글이 없습니다.", "C4042"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.", "U4041"),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우중이 아닙니다.", "F4041"),

    /* 409 */
    POST_ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요 한 게시물입니다.", "P4091"),
    COMMENT_ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요 한 댓글입니다.", "C4091"),
    USER_ALREADY_FOLLOWING(HttpStatus.CONFLICT, "이미 팔로우중인 대상입니다.", "F4091"),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 username입니다.", "U4091"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용중인 email입니다.", "U4092"),

    /* 500 */
    FAILED_TO_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다", "C5001")
    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    @Override
    public String toString() {
        return this.status + " " + message + " " + code;
    }
}
