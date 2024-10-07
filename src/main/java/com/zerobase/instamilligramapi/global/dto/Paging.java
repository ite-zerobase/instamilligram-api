package com.zerobase.instamilligramapi.global.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class Paging {
    @Min(value = 1, message = "page는 1보다 커야 합니다.")
    private int page = 1;
    @Max(value = 10, message = "size는 10 이하여야 합니다.")
    private int size = 10;
    @NotNull(message = "requestingUser은 null일 수 없습니다.")
    private String requestingUser;
}
