package net.webius.myassets.global.auth.dto;

import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("SignupReq 테스트")
public class SignupReqTest {
    private final Validator validator;

    @Autowired
    public SignupReqTest(Validator validator) {
        this.validator = validator;
    }

    private SignupReq createValidObject() {
        SignupReq req = new SignupReq();
        req.setUsername("user");
        req.setPassword("AbCd123#");
        req.setPasswordConfirm("AbCd123#");
        req.setBirthday(LocalDate.of(2000, 1, 1));
        return req;
    }

    @Test @DisplayName("정상")
    public void valid() {
        var req = createValidObject();

        var violations = validator.validate(req);
        assertThat(violations).isEmpty();
    }

    @Test @DisplayName("@NotBlank 제약 조건 위배")
    public void invalidNotBlank() {
        var req = createValidObject();
        req.setUsername("   ");
        req.setPassword("");
        req.setPasswordConfirm("");

        var violations = validator.validate(req);
        assertThat(violations)
                .filteredOn(v -> v.getMessageTemplate().equals("{jakarta.validation.constraints.NotBlank.message}"))
                .hasSize(3);
    }

    @Test @DisplayName("@NotNull 제약 조건 위배")
    public void invalidNotNull() {
        var req = createValidObject();
        req.setBirthday(null);

        var violations = validator.validate(req);
        assertThat(violations)
                .filteredOn(v -> v.getMessageTemplate().equals("{jakarta.validation.constraints.NotNull.message}"))
                .hasSize(1);
    }

    @Test
    @DisplayName("@Password 비밀번호 제약 조건 위배")
    public void invalidPassword() {
        var req = createValidObject();
        req.setPassword("a b");
        req.setPasswordConfirm("a b");

        var violations = validator.validate(req);
        assertThat(violations)
                .filteredOn(v -> v.getMessageTemplate().contains("validation.constraints.PasswordValidator"))
                .hasSize(3); // Contains, Size, Space
    }

    @Test @DisplayName("@FieldEquals 비밀번호 일치 조건 위배")
    public void invalidPasswordEquals() {
        var req = createValidObject();
        req.setPasswordConfirm("password123");

        var violations = validator.validate(req);
        assertThat(violations)
                .filteredOn(v -> v.getMessageTemplate().equals("{validation.constraints.FieldEqualsConstraint.message}"))
                .isNotEmpty();
    }

    @Test @DisplayName("@PastOrPresent 생년월일 제약 조건 위배")
    public void invalidBirthday() {
        var req = createValidObject();
        req.setBirthday(LocalDate.now().plusDays(1));

        var violations = validator.validate(req);
        assertThat(violations)
                .filteredOn(v -> v.getMessageTemplate().equals("{jakarta.validation.constraints.PastOrPresent.message}"))
                .isNotEmpty();
    }
}
