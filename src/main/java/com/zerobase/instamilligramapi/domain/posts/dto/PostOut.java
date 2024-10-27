package com.zerobase.instamilligramapi.domain.posts.dto;

import com.zerobase.instamilligramapi.domain.comments.dto.CommentOut;
import com.zerobase.instamilligramapi.domain.users.dto.UserOut;
import com.zerobase.instamilligramapi.global.dto.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostOut extends AuditDto {
    private Integer postId;
    private UserOut writer;
    private String caption;
    private String place;
    private int likeCount;
    private int commentCount;
    private boolean liked;
    private LocalDateTime likedAt;
    private List<PostMediaOut> media;
}
