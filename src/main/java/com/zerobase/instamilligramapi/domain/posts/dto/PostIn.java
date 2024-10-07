package com.zerobase.instamilligramapi.domain.posts.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PostIn{
    @JsonIgnore
    private Integer postId;
    @NotNull(message = "username 필드는 null일 수 없습니다.")
    private String username;
    @NotNull(message = "caption 필드는 null일 수 없습니다.")
    private String caption;
    private String place;
    private List<PostMediaIn> media;
    @JsonIgnore
    private String requestingUser;
}
