package com.zerobase.instamilligramapi.domain.posts.dto;

import com.zerobase.instamilligramapi.global.dto.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostOut extends AuditDto {
    private int postId;
    private int userId;
    private String caption;
    private String place;
    private int likesCount;
    private int commentsCount;
    private boolean liked;
    private int likedAt;
    private List<PostMedia> mediaUrl;
}
