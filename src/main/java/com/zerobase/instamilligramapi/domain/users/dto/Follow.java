package com.zerobase.instamilligramapi.domain.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Follow {
    private String follower;
    private String followee;

    public static Follow follower(String username) {
        if (username == null) username = "";
        Follow follow = new Follow();
        follow.setFollower(username);
        return follow;
    }
    public Follow isFollowing(String username) {
        if (username == null) username = "";
        this.followee = username;
        return this;
    }
}
