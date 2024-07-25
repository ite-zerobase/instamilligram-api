package com.zerobase.instamilligramapi.domain.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthIn {
    @NotNull(message = "username은 null일 수 없습니다.")
    private String username;
    @NotNull(message = "password는 null일 수 없습니다.")
    private String password;
    @JsonIgnore
    private String passwordHash;
}
