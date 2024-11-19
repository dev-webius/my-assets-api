package net.webius.myassets.annotation.validator;

import jakarta.validation.Validator;
import net.webius.myassets.annotation.validator.domain.MockPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("PasswordValidator 테스트")
public class PasswordValidatorTest {
    private final Validator validator;

    @Autowired
    public PasswordValidatorTest(Validator validator) {
        this.validator = validator;
    }

    @Test @DisplayName("정상")
    public void valid() {
        var password = new MockPassword("Atest123#");

        var violations = validator.validate(password);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("비밀번호 공백 제약 조건 위배")
    public void noSpace() {
        var password = new MockPassword("Abcd    efgh1!");

        var violations = validator.validate(password);
        assertThat(violations)
                .filteredOn(v -> v.getMessageTemplate().equals("{validation.constraints.Password.Space.message}"))
                .isNotEmpty();
    }

    @Test @DisplayName("@Password 비밀번호 글자수 제한 조건 위배")
    public void size() {
        // 8자 미만
        var underPassword = new MockPassword("Ab1!");

        // 20자 초과
        var overPassword = new MockPassword("Ab1!Ab1!Ab1!Ab1!Ab1!a");

        var passwords = new MockPassword[] {underPassword, overPassword};
        for (var password : passwords) {
            var violations = validator.validate(password);
            assertThat(violations)
                    .filteredOn(v -> v.getMessageTemplate().equals("{validation.constraints.Password.Size.message}"))
                    .isNotEmpty();
        }
    }

    @Test @DisplayName("@Password 비밀번호 제약 조건 위배")
    public void invalidPassword() {
        // 대문자 누락
        var noUpperCase = new MockPassword("abcd1234!@#$");

        // 소문자 누락
        var noLowerCase = new MockPassword("ABCD1234!@#$");

        // 숫자 누락
        var noDigit = new MockPassword("ABCDabcd!@#$");

        // 특수문자 누락
        var noSpecial = new MockPassword("ABCDabcd1234");

        var passwords = new MockPassword[] {noUpperCase, noLowerCase, noDigit, noSpecial};
        for (var password : passwords) {
            var violations = validator.validate(password);
            assertThat(violations)
                    .filteredOn(v -> v.getMessageTemplate().equals("{validation.constraints.Password.Contains.message}"))
                    .isNotEmpty();
        }
    }
}
