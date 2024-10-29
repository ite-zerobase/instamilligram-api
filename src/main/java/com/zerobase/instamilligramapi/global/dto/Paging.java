package com.zerobase.instamilligramapi.global.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title="PAGE_DTO")
public class Paging {
    @Schema(description = "페이지")
    @NotNull(message = "페이지는 필수값입니다")
    @Min(value = 1, message = "page는 1보다 커야 합니다.")
    private int page = 1;
    @Schema(description = "조회 사이즈")
    @NotNull(message = "조회 사이즈는 필수값입니다.")
    @Min(value = 1, message = "한 번에 조회 가능한 게시글 수는 최소 1개에서 최대 20개 입니다. (기본값 10)")
    @Max(value = 20, message = "한 번에 조회 가능한 게시글 수는 최소 1개에서 최대 20개 입니다. (기본값 10)")
    private int size = 10;
    @JsonIgnore
    private String requestingUser;
}

