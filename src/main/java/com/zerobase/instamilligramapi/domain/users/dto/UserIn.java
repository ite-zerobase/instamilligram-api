package com.zerobase.instamilligramapi.domain.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Schema(title="USER_REQ_DTO: 회원가입 요청 DTO")
public class UserIn {
    @JsonIgnore
    private Integer userId;
    @NotBlank(message = "사용자 이름을 입력해 주세요.")
    @Schema(description = "사용자 이름", example = "afsf")
    private String username;
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Schema(description = "비밀번호")
    private String password;
    @JsonIgnore
    private String passwordHash;
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Schema(description = "사용자 이메일")
    private String email;
    @JsonIgnore
    private String profilePictureUrl;
    @Schema(description = "소개글")
    private String bio;
    @Schema(description = "프로필 이미지", type = "string", format = "binary")
    private MultipartFile profileImage;
}
