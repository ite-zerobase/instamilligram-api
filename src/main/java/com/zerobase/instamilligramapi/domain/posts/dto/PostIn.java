package com.zerobase.instamilligramapi.domain.posts.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zerobase.instamilligramapi.global.dto.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostIn extends AuditDto {
    @JsonIgnore
    private Integer postId;
    private Integer userId;
    private String caption;
    private String place;
    private List<String> mediaUrl;
}
