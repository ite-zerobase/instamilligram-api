package com.zerobase.instamilligramapi.domain.comments.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title="댓글 작성 DTO")
public class CommentIn {
    @JsonIgnore
    private Integer commentId;
    @JsonIgnore
    private Integer postId;
    @JsonIgnore
    private String username;
    @NotNull(message = "댓글 내용을 입력해 주세요.")
    @Schema(description = "댓글 내용")
    private String commentText;
    @Schema(description = "상위 댓글 ID", required = false)
    private Integer parentId;
}
