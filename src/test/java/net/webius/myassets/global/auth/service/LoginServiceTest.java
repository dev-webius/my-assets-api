package net.webius.myassets.global.auth.service;

import net.webius.myassets.global.auth.dto.LoginReq;
import net.webius.myassets.global.auth.dto.SignupReq;
import net.webius.myassets.global.auth.exception.UsernameNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest @DisplayName("LoginService 테스트")
public class LoginServiceTest {
    private final LoginService loginService;
    private final SignupService signupService;

    @Autowired
    public LoginServiceTest(LoginService loginService, SignupService signupService) {
        this.loginService = loginService;
        this.signupService = signupService;
    }

    @Test @DisplayName("로그인 테스트")
    public void login() {
        SignupReq signupReq = new SignupReq();
        signupReq.setUsername("logintest");
        signupReq.setPassword("123456Aa##");
        signupReq.setPasswordConfirm("123456Aa##");
        signupReq.setBirthday(LocalDate.now());
        signupService.signup(signupReq);

        LoginReq loginReq = new LoginReq();
        loginReq.setUsername("logintest");
        loginReq.setPassword("123456Aa##");
        loginService.login(loginReq);
    }

    @Test @DisplayName("존재하지 않는 사용자 로그인 테스트")
    public void notFound() {
        LoginReq loginReq = new LoginReq();
        loginReq.setUsername("anonymous");
        loginReq.setPassword("123456Aa##");

        assertThatThrownBy(() -> loginService.login(loginReq))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
