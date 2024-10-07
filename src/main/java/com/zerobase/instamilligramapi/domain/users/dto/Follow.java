package com.zerobase.instamilligramapi.domain.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Follow {
    @JsonIgnore
    private String requestingUser;
    private String follower;
    private String followee;
    private String followedAt;
}
