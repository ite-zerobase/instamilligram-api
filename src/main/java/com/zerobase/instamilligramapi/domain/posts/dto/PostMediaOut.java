package com.zerobase.instamilligramapi.domain.posts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zerobase.instamilligramapi.global.dto.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostMediaOut extends AuditDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer mediaSeq;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer postId;
    private String mediaUrl;
    private String mediaType;
}
