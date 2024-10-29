package com.zerobase.instamilligramapi.domain.login;

import com.zerobase.instamilligramapi.domain.login.dto.AuthIn;
import com.zerobase.instamilligramapi.domain.login.dto.TokenOut;
import com.zerobase.instamilligramapi.domain.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @Operation(summary = "로그인 API", description = """
            사용자 아이디, 비밀번호를 받은 뒤 토큰 반환 <br>
            """)
    @PostMapping("")
    public ResponseEntity<?> createUserAuth(@RequestBody @Valid AuthIn authIn) {
        String token = userService.login(authIn);
        TokenOut tokenOut = new TokenOut();
        tokenOut.setAccessToken(token);
        return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(tokenOut);
    }
}
