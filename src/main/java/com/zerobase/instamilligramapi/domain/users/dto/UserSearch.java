package com.zerobase.instamilligramapi.domain.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSearch {
    @NotNull(message = "username은 null일 수 없습니다.")
    private String username;
    @JsonIgnore
    private String currentUsername;
    @JsonIgnore
    private boolean detailed;

    public static UserSearch username(String u) {
        UserSearch userSearch = new UserSearch();
        userSearch.setUsername(u);
        return userSearch;
    }
}
