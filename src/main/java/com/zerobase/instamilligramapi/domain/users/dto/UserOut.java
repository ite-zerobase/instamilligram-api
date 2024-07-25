package com.zerobase.instamilligramapi.domain.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zerobase.instamilligramapi.domain.posts.dto.PostOut;
import com.zerobase.instamilligramapi.global.dto.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserOut extends AuditDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer userId;
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @JsonIgnore
    private String passwordHash;
    private String profilePictureUrl;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bio;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Profile profile;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PostOut> posts;
}
