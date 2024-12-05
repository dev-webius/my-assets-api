package net.webius.myassets.global.auth.dto;

import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("LoginReq 테스트")
public class LoginReqTest {
    private final Validator validator;

    @Autowired
    public LoginReqTest(Validator validator) {
        this.validator = validator;
    }

    private LoginReq createValidObject() {
        LoginReq loginReq = new LoginReq();
        loginReq.setUsername("admin");
        loginReq.setPassword("admin");
        return loginReq;
    }

    @Test @DisplayName("정상")
    public void valid() {
        var req = createValidObject();

        assertThat(validator.validate(req)).isEmpty();
    }

    @Test @DisplayName("@NotBlank 위배")
    public void invalidNotBlank() {
        var req = createValidObject();
        req.setUsername(null);
        req.setPassword("   ");

        assertThat(validator.validate(req))
                .filteredOn(v -> v.getMessageTemplate().equals("{jakarta.validation.constraints.NotBlank.message}"))
                .hasSize(2);
    }
}
