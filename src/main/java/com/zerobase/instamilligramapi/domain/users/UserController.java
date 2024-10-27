package com.zerobase.instamilligramapi.domain.users;

import com.zerobase.instamilligramapi.domain.users.dto.Follow;
import com.zerobase.instamilligramapi.domain.users.dto.ImageOut;
import com.zerobase.instamilligramapi.domain.users.dto.UserIn;
import com.zerobase.instamilligramapi.domain.users.dto.UserOut;
import com.zerobase.instamilligramapi.global.dto.BaseResponse;
import com.zerobase.instamilligramapi.global.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "유저 조회 API", description = "username의 정보 반환. requesting-user는 대상 유저 정보를 조회하고자 하는 사람.")
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@RequestHeader("requesting-user") String requestingUser, @PathVariable String username) {
        UserOut user = userService.selectUserByUsername(username, requestingUser);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "유저 생성 API", description = "신규 유저 생성")
    @PostMapping(value="")
    public ResponseEntity<UserOut> createUser(@Valid @RequestBody UserIn user) {
        UserOut userOut = userService.createUser(user);
        return ResponseEntity.ok(userOut);
    }

    @Operation(summary = "유저 프로필 업로드 API", description = "유저 프로필 추가")
    @PostMapping(value="/{username}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageOut> uploadUserProfile(@PathVariable String username, @RequestParam MultipartFile image) {
        ImageOut result = userService.uploadUserProfile(username, image);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "팔로우 생성 API", description = "requesting-user이 팔로우를 누른 사람. username은 팔로우 대상")
    @PostMapping("/{username}/follow")
    public ResponseEntity<Follow> followUser(HttpServletRequest request, @PathVariable String username) {
        String requester = jwtUtil.extractUsernameFromHeader(request);
        Follow result = userService.insertFollow(username, requester);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "팔로우 삭제 API", description = "requesting-user가 username을 언팔로우")
    @DeleteMapping("/{username}/follow")
    public ResponseEntity<BaseResponse> removeFollow(HttpServletRequest request, @PathVariable String username) {
        String requester = jwtUtil.extractUsernameFromHeader(request);
        userService.removeFollow(username, requester);
        return ResponseEntity.ok(BaseResponse.success());
    }
}
