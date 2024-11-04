package com.zerobase.instamilligramapi.domain.comments.dto;

import com.zerobase.instamilligramapi.global.dto.AuditDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentOut extends AuditDto {
    @Schema(description = "댓글 고유 아이디", example = "1")
    private Integer commentId;
    @Schema(description = "댓글이 위치한 게시글의 아이디", example = "1")
    private Integer postId;
    @Schema(description = "댓글 작성자 username", example = "danielchoi1115")
    private String createdBy;
    private String creatorProfileUrl;
    @Schema(description = "댓글 내용", example = "댓글입니다.")
    private String commentText;
    private Integer parentId;
    private Integer likeCount;
    private boolean liked;
    private LocalDateTime likedAt;
    private Integer replyCount;
}
