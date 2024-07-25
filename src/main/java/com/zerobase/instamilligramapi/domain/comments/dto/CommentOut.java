package com.zerobase.instamilligramapi.domain.comments.dto;

import com.zerobase.instamilligramapi.global.dto.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentOut extends AuditDto {
    private Integer commentId;
    private Integer postId;
    private String createdBy;
    private String commentText;
    private Integer parentId;
    private Integer likeCount;
    private boolean liked;
    private LocalDateTime likedAt;
    private Integer replyCount;
}
