package com.zerobase.instamilligramapi.domain.users;

import com.zerobase.instamilligramapi.domain.users.dto.AuthIn;
import com.zerobase.instamilligramapi.domain.users.dto.Follow;
import com.zerobase.instamilligramapi.domain.users.dto.UserIn;
import com.zerobase.instamilligramapi.domain.users.dto.UserOut;
import com.zerobase.instamilligramapi.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인 API", description = "사용자 아이디, 비밀번호를 받은 뒤 유저 정보를 반환")
    @PostMapping("/auth")
    public ResponseEntity<UserOut> createUserAuth(@RequestBody @Valid AuthIn authIn) {
        UserOut user = userService.selectUserByAuth(authIn);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "유저 조회 API", description = "username으로 유저 정보 반환")
    @GetMapping("/{username}")
    public ResponseEntity<UserOut> getUserByUsername(@PathVariable String username) {
        UserOut user = userService.selectUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "유저 생성 API", description = "신규 유저 생성")
    @PostMapping("")
    public ResponseEntity<UserOut> createUser(@RequestBody UserIn userIn) {
        UserOut user = userService.insertUser(userIn);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "팔로우 API", description = "팔로우")
    @PostMapping("/{username}/follow")
    public ResponseEntity<BaseResponse> followUser(@RequestHeader("current-username") String currentUsername, @PathVariable String username) {
        Follow follow = Follow.follower(currentUsername).isFollowing(username);
        userService.followUser(follow);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @Operation(summary = "언팔로우 API", description = "언팔로우")
    @DeleteMapping("/{username}/follow")
    public ResponseEntity<BaseResponse> unfollowUser(@RequestHeader("current-username") String currentUsername, @PathVariable String username) {
        Follow follow = Follow.follower(currentUsername).isFollowing(username);
        userService.unfollowUser(follow);
        return ResponseEntity.ok(BaseResponse.success());
    }
}
