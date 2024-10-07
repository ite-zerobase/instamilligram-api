package com.zerobase.instamilligramapi.domain.users;

import com.zerobase.instamilligramapi.domain.users.dto.AuthIn;
import com.zerobase.instamilligramapi.domain.users.dto.Follow;
import com.zerobase.instamilligramapi.domain.users.dto.UserIn;
import com.zerobase.instamilligramapi.domain.users.dto.UserOut;
import com.zerobase.instamilligramapi.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "유저 조회 API", description = "username으로 유저 정보 반환. requesting-user는 대상 유저 정보를 조회하고자 하는 사람.")
    @GetMapping("/{username}")
    public ResponseEntity<UserOut> getUserByUsername(@RequestHeader("requesting-user") String requestingUser, @PathVariable String username) {
        UserOut user = userService.selectUserByUsername(username, requestingUser);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "유저 생성 API", description = "신규 유저 생성")
    @PostMapping("")
    public ResponseEntity<UserOut> createUser(@RequestBody UserIn userIn) {
        UserOut user = userService.insertUser(userIn);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "팔로우 생성 API", description = "requesting-user이 팔로우를 누른 사람. username은 팔로우 대상")
    @PostMapping("/{username}/follow")
    public ResponseEntity<Follow> followUser(@RequestHeader("requesting-user") String requestingUser, @PathVariable String username) {
        Follow result = userService.insertFollow(username, requestingUser);
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "팔로우 삭제 API", description = "requesting-user가 username 팔로우 중단")
    @DeleteMapping("/{username}/follow")
    public ResponseEntity<BaseResponse> removeFollow(@RequestHeader("requesting-user") String requestingUser, @PathVariable String username) {
        userService.removeFollow(username, requestingUser);
        return ResponseEntity.ok(BaseResponse.success());
    }
}
