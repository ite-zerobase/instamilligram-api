package com.zerobase.instamilligramapi.domain.posts.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zerobase.instamilligramapi.global.dto.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class PostMediaIn {
    @JsonIgnore
    private Integer mediaSeq;
    @JsonIgnore
    private Integer postId;
    private String mediaUrl;
    private String mediaType;
}
