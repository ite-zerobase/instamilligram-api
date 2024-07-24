package com.zerobase.instamilligramapi.global.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 */
    BAD_BODY_REQUEST(HttpStatus.BAD_REQUEST, "Wrong Body Format.", "C4001"), // C4001 <- C (Common) 400 (Status code)  1 (SEQ)
    BAD_QUERY_REQUEST(HttpStatus.BAD_REQUEST, "Wrong Query Format.", "C4002"),

    /* 404 */
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글이 없습니다.", "P4041"),

    /* 409 */
    PORTFOLIO_ASSET_DTL_CONFLICT(HttpStatus.CONFLICT, "PORTFOLIO_ASSET_DTL Conflicts.", "P4091")


    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    @Override
    public String toString() {
        return this.status + " " + message + " " + code;
    }
}
