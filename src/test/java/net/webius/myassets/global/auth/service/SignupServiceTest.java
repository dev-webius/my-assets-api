package net.webius.myassets.global.auth.service;

import net.webius.myassets.global.auth.dto.SignupReq;
import net.webius.myassets.global.auth.exception.UsernameAlreadyUsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest @DisplayName("SignupService 테스트")
public class SignupServiceTest {
    private final SignupService signupService;

    @Autowired
    public SignupServiceTest(SignupService signupService) {
        this.signupService = signupService;
    }

    @Test @DisplayName("회원가입 테스트")
    public void signup() {
        SignupReq signupReq = new SignupReq();
        signupReq.setUsername("testuser");
        signupReq.setPassword("testPass12@");
        signupReq.setPasswordConfirm("testPass12@");
        signupReq.setBirthday(LocalDate.now());

        signupService.signup(signupReq);

        // 중복 가입 방지
        assertThatThrownBy(() -> signupService.signup(signupReq))
                .isInstanceOf(UsernameAlreadyUsingException.class);
    }
}
