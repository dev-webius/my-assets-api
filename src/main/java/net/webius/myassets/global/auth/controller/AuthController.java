package net.webius.myassets.global.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import net.webius.myassets.global.auth.dto.LoginReq;
import net.webius.myassets.global.auth.dto.SignupReq;
import net.webius.myassets.global.auth.service.SignupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tags({
        @Tag(name = "All"),
        @Tag(name = "Global"),
        @Tag(name = "Auth"),
})
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final SignupService signupService;

    public AuthController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginReq loginReq) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "201", description = "성공")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupReq signupReq) {
        signupService.signup(signupReq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
