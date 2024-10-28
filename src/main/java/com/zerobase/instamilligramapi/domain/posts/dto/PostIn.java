package com.zerobase.instamilligramapi.domain.posts.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title="POST_REQ_DTO: Post 생성 DTO")
public class PostIn{
    @JsonIgnore
    private Integer postId;
    @JsonIgnore
    private String username;
    @NotBlank(message = "게시글 내용을 입력해 주세요")
    @Schema(description = "게시글 내용")
    private String postContent;
    @Schema(description = "장소")
    private String place;
    @NotEmpty(message = "사진을 최소한 한 장 이상 업로드해 주세요.")
    @Schema(description = "게시글 사진", type = "array")
    private List<MultipartFile> images;
    @JsonIgnore
    private String requestingUser;
}
