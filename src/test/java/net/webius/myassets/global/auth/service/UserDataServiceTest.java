package net.webius.myassets.global.auth.service;

import net.webius.myassets.global.auth.dto.SignupReq;
import net.webius.myassets.global.auth.exception.PasswordMismatchException;
import net.webius.myassets.global.auth.exception.UsernameAlreadyUsingException;
import net.webius.myassets.global.auth.exception.UsernameNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest @DisplayName("UserDataService 테스트")
public class UserDataServiceTest {
    private final UserDataService userDataService;
    private final SignupService signupService;

    @Autowired
    public UserDataServiceTest(UserDataService userDataService, SignupService signupService) {
        this.userDataService = userDataService;
        this.signupService = signupService;
    }

    @Test @DisplayName("비밀번호 암호화")
    public void encodePassword() {
        String plainPassword = "test1234";
        String encodedPassword = userDataService.encodePassword(plainPassword);

        assertThat(encodedPassword).isNotEqualTo(plainPassword);
    }

    @Test @DisplayName("비밀번호 검증")
    public void verifyPassword() {
        String plainPassword = "test1234";
        String encodedPassword = userDataService.encodePassword(plainPassword);

        userDataService.verifyPassword(plainPassword, encodedPassword);

        assertThatThrownBy(() -> userDataService.verifyPassword("anotherPassword", encodedPassword))
                .isInstanceOf(PasswordMismatchException.class);
    }

    @Test @DisplayName("사용자명 고유성 검증")
    public void verifyUniqueUsername() {
        String uniqueUsername = "unique_username";
        userDataService.verifyUniqueUsername(uniqueUsername);

        SignupReq signupReq = new SignupReq();
        signupReq.setUsername(uniqueUsername);
        signupReq.setPassword("uniquePassword12#");
        signupReq.setPasswordConfirm("uniquePassword12#");
        signupReq.setBirthday(LocalDate.now());
        signupService.signup(signupReq);

        assertThatThrownBy(() -> userDataService.verifyUniqueUsername(uniqueUsername))
                .isInstanceOf(UsernameAlreadyUsingException.class);
    }

    @Test @DisplayName("사용자명 탐색")
    public void findByUsername() {
        String findUsername = "find_username";
        assertThatThrownBy(() -> userDataService.verifyUserExists(findUsername))
                .isInstanceOf(UsernameNotFoundException.class);

        SignupReq signupReq = new SignupReq();
        signupReq.setUsername(findUsername);
        signupReq.setPassword("findPassword12#");
        signupReq.setPasswordConfirm("findPassword12#");
        signupReq.setBirthday(LocalDate.now());
        signupService.signup(signupReq);

        var user = userDataService.findByUsername(findUsername);
        assertThat(user.getUsername()).isEqualTo(findUsername);
    }
}
