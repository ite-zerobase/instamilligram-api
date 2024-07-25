package com.zerobase.instamilligramapi.domain.users.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Follow {
    @NotNull(message = "username은 null일 수 없습니다")
    private String username;
    @NotNull(message = "followee는 null일 수 없습니다")
    private String followee;
}
