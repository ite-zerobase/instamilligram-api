package com.zerobase.instamilligramapi.domain.comments.dto;

import com.zerobase.instamilligramapi.global.dto.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentOut extends AuditDto {
    private Integer comment_id;
    private Integer post_id;
    private Integer user_id;
    private String comment_text;
    private Integer parent_id;
    private Integer like_count;
    private Integer reply_count;
}
