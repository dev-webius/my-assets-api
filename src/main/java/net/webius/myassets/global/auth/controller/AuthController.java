package net.webius.myassets.global.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.webius.myassets.global.auth.dto.LoginReq;
import net.webius.myassets.global.auth.dto.SignupReq;
import net.webius.myassets.global.auth.dto.UserSessionRes;
import net.webius.myassets.global.auth.service.JwtService;
import net.webius.myassets.global.auth.service.LoginService;
import net.webius.myassets.global.auth.service.SignupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Global")
@Tag(name = "Auth")
@RestController @RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    private final LoginService loginService;
    private final SignupService signupService;
    private final JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "로그인")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "400", description = "사용자 인증 실패")
    public ResponseEntity<UserSessionRes> login(@RequestBody @Valid LoginReq loginReq) {
        loginService.login(loginReq);
        var response = jwtService.createUserSession(loginReq.getUsername());
        return ResponseEntity.ok().body(response);
    }

    // POST /v1/auth/logout 의 경우 필요 시 구현, Redis 에 토큰을 저장하도록

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "201", description = "회원가입 성공")
    @ApiResponse(responseCode = "400", description = "사용자 등록 실패")
    public ResponseEntity<UserSessionRes> signup(@RequestBody @Valid SignupReq signupReq) {
        signupService.signup(signupReq);
        var response = jwtService.createUserSession(signupReq.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
