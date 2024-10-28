package com.zerobase.instamilligramapi.domain.users;

import com.zerobase.instamilligramapi.domain.users.dto.Follow;
import com.zerobase.instamilligramapi.domain.users.dto.UserIn;
import com.zerobase.instamilligramapi.domain.users.dto.UserOut;
import com.zerobase.instamilligramapi.global.dto.BaseResponse;
import com.zerobase.instamilligramapi.global.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "유저 조회 API", description = "사용자 정보 조회")
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(HttpServletRequest request, @PathVariable String username) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        UserOut user = userService.selectUserByUsername(username, requester);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "유저 생성 API", description = "신규 유저 생성")
    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserOut> createUser(@ModelAttribute @Valid UserIn user) {
        UserOut userOut = userService.createUser(user);
        return ResponseEntity.ok(userOut);
    }

//    @Operation(summary = "유저 프로필 업로드 API", description = "유저 프로필 추가")
//    @PostMapping(value="/{username}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ImageOut> uploadUserProfile(@PathVariable String username, @RequestParam MultipartFile image) {
//        ImageOut result = userService.uploadUserProfile(username, image);
//        return ResponseEntity.ok(result);
//    }

    @Operation(summary = "팔로우 생성 API", description = """
            현재 로그인 한 사용자가 대상 사용자를 팔로우. <br>
            예시) <br>
            현재 사용자인 user1이 /user2/follow 로 요청 시 user1 -> user2 팔로우 관계 생성
            """)
    @PostMapping("/{username}/follow")
    public ResponseEntity<Follow> followUser(HttpServletRequest request, @PathVariable String username) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        Follow result = userService.insertFollow(username, requester);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "팔로우 삭제 API", description = """
            현재 로그인 한 사용자가 대상 사용자를 언팔로우. <br>
            예시) <br>
            현재 사용자인 user1이 /user2/follow 로 요청 시 user1 -> user2 팔로우 관계 삭제
            """)
    @DeleteMapping("/{username}/follow")
    public ResponseEntity<BaseResponse> removeFollow(HttpServletRequest request, @PathVariable String username) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        userService.removeFollow(username, requester);
        return ResponseEntity.ok(BaseResponse.success());
    }
}
