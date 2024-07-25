package com.zerobase.instamilligramapi.domain.comments.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zerobase.instamilligramapi.global.dto.AuditDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CommentIn {
    @JsonIgnore
    private Integer commentId;
    @NotNull(message = "postId 필드는 null일 수 없습니다.")
    private Integer postId;
    @NotNull(message = "username 필드는 null일 수 없습니다.")
    private String username;
    @NotNull(message = "commentText 필드는 null일 수 없습니다.")
    private String commentText;
    private Integer parentId;
}
