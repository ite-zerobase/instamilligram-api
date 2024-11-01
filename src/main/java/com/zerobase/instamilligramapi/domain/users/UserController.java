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

    @Operation(summary = "사용자 조회 API", description = """
            사용자 정보 조회 <br>
            <pre>
                {
                  "createdAt": 사용자 생성일자
                  "updatedAt": 사용자 수정일자
                  "userId": 사용자 ID
                  "username": 사용자명
                  "following": 해당 사용자 팔로잉 여부
                  "email": 사용자 이메일
                  "profilePictureUrl": 사용자 프로필 이미지
                  "nickname": "사용자 닉네임"
                  "job": "사용자 직업"
                  "bio": 사용자 소개글
                  "postCount": 게시글 수
                  "followerCount": 팔로워 수
                  "followingCount": 팔로잉 수
                }
            </pre>
            """)
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(HttpServletRequest request, @PathVariable String username) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        UserOut user = userService.selectUserByUsername(username, requester);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "신규 가입", description = "신규 사용자 생성")
    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserOut> createUser(@ModelAttribute @Valid UserIn user) {
        UserOut userOut = userService.createUser(user);
        return ResponseEntity.ok(userOut);
    }

//    @Operation(summary = "사용자 프로필 업로드 API", description = "사용자 프로필 추가")
//    @PostMapping(value="/{username}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ImageOut> uploadUserProfile(@PathVariable String username, @RequestParam MultipartFile image) {
//        ImageOut result = userService.uploadUserProfile(username, image);
//        return ResponseEntity.ok(result);
//    }

    @Operation(summary = "사용자 팔로우", description = """
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

    @Operation(summary = "사용자 언팔로우", description = """
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
