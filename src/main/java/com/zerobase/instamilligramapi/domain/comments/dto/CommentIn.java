package com.zerobase.instamilligramapi.domain.comments.dto;

import com.zerobase.instamilligramapi.global.dto.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentIn extends AuditDto {
    private Integer post_id;
    private Integer user_id;
    private String comment_text;
    private Integer parent_id;
}
