package net.webius.myassets.annotation.validator;

import jakarta.validation.Validator;
import net.webius.myassets.annotation.validator.domain.Identifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("IdentifierPatternValidator 테스트")
public class IdentifierPatternValidatorTest {
    private final Validator validator;

    @Autowired
    public IdentifierPatternValidatorTest(Validator validator) {
        this.validator = validator;
    }

    @Test @DisplayName("정상")
    public void valid() {
        var identifiers = new Identifier[] {
                new Identifier("user"),
                new Identifier("user-001"),
                new Identifier("user_001"),
        };

        for (var identifier : identifiers) {
            assertThat(validator.validate(identifier)).isEmpty();
        }
    }

    @Test @DisplayName("공백 위배")
    public void invalidNoSpace() {
        var identifier = new Identifier("us er");

        assertThat(validator.validate(identifier))
                .filteredOn(v -> v.getMessageTemplate().equals("{validation.constraints.NoSpace.message}"))
                .isNotEmpty();
    }

    @Test @DisplayName("관리자 위배")
    public void invalidNoAdmin() {
        var identifier = new Identifier("admin");

        assertThat(validator.validate(identifier))
                .filteredOn(v -> v.getMessageTemplate().equals("{validation.constraints.NoAdmin.message}"))
                .isNotEmpty();
    }

    @Test @DisplayName("패턴 위배")
    public void invalidPattern() {
        var identifier = new Identifier("admin1!");

        assertThat(validator.validate(identifier))
                .filteredOn(v -> v.getMessageTemplate().equals("{validation.constraints.IdentifierPattern.message}"))
                .isNotEmpty();
    }
}
