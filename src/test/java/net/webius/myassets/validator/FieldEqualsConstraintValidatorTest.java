package net.webius.myassets.validator;

import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import net.webius.myassets.component.MessageSourceProvider;
import net.webius.myassets.exception.InvalidFieldType;
import net.webius.myassets.validator.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest @DisplayName("FieldEqualsConstraintValidator 테스트")
public class FieldEqualsConstraintValidatorTest {
    private final Validator validator;
    private final MessageSourceProvider messageSourceProvider;

    @Autowired
    public FieldEqualsConstraintValidatorTest(Validator validator, MessageSourceProvider messageSourceProvider) {
        this.validator = validator;
        this.messageSourceProvider = messageSourceProvider;
    }

    @Test @DisplayName("필드가 일치하는 경우")
    public void equalFields() {
        PasswordEquals equals = new PasswordEquals("test123", "test123");

        var violations = validator.validate(equals);
        assertThat(violations).isEmpty();
    }

    @Test @DisplayName("필드가 일치하지 않는 경우")
    public void notEqualsField() {
        PasswordEquals equals = new PasswordEquals("test123", "test123ABC");

        var violations = validator.validate(equals);
        var violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo(messageSourceProvider.get("validation.constraints.FieldEqualsConstraint.message"));
        assertThat(violation.getPropertyPath().toString())
                .isEqualTo("passwordConfirm>password");
    }

    @Test @DisplayName("다중 필드가 모두 일치하는 경우")
    public void equalMultipleFields() {
        MultipleEquals equals = new MultipleEquals("test123", "test123", "test123");

        var violations = validator.validate(equals);
        assertThat(violations).isEmpty();
    }

    @Test @DisplayName("다중 필드가 하나라도 일치하지 않는 경우")
    public void notEqualsMultipleField() {
        MultipleEquals equals1 = new MultipleEquals("test123", "test123ABC", "test123");
        MultipleEquals equals2 = new MultipleEquals("test123", "test123", "test123ABC");

        var violations = validator.validate(equals1);
        var violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo(messageSourceProvider.get("validation.constraints.FieldEqualsConstraint.message"));
        assertThat(violation.getPropertyPath().toString())
                .isEqualTo("field3>field2");

        violations = validator.validate(equals2);
        assertThat(violations).hasSize(2);
    }

    @Test @DisplayName("여러 필드가 모두 일치하지 않는 경우")
    public void notEqualFields() {
        PrimitiveEquals equals = new PrimitiveEquals(
                123, 1234,
                1000, 10000,
                1E+1, 1E+2,
                true, false);

        var violations = validator.validate(equals);
        var violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo(messageSourceProvider.get("validation.constraints.FieldEqualsConstraint.message"));
        assertThat(violations).hasSize(4);
    }

    @Test @DisplayName("원시 타입을 비교하는 경우 (자동 래핑 비교)")
    public void equalPrimitiveFields() {
        PrimitiveEquals equals = new PrimitiveEquals(
                123, 123,
                1000000, 1000000,
                1E+10, 1E+10,
                true, true);

        var violations = validator.validate(equals);
        assertThat(violations).isEmpty();
    }

    @Test @DisplayName("원본 필드가 Null 인 경우")
    public void nullSourceField() {
        PasswordEquals equals = new PasswordEquals("test123", null);

        var violations = validator.validate(equals);
        assertThat(violations).hasSize(1);
    }

    @Test @DisplayName("대상 필드가 Null 인 경우")
    public void nullTargetField() {
        PasswordEquals equals = new PasswordEquals(null, "test123");

        var violations = validator.validate(equals);
        assertThat(violations).hasSize(1);
    }

    @Test @DisplayName("null 데이터를 비교하는 경우")
    public void equalsNullField() {
        PasswordEquals equals = new PasswordEquals(null, null);

        var violations = validator.validate(equals);
        assertThat(violations).isEmpty();
    }

    @Test @DisplayName("필드를 찾지 못하는 경우")
    public void equalsNoSuchField() {
        EqualsNoSuchField equals = new EqualsNoSuchField();

        assertThatThrownBy(() -> validator.validate(equals))
                .isInstanceOf(ValidationException.class)
                .rootCause()
                .isInstanceOf(NoSuchFieldException.class);
    }

    @Test @DisplayName("필드를 전달하지 않은 경우")
    public void equalsNoProvidedField() {
        EqualsNoProvidedField equals = new EqualsNoProvidedField();

        var violations = validator.validate(equals);
        assertThat(violations).isEmpty();
    }

    @Test @DisplayName("필드 타입이 일치하지 않는 경우")
    public void mismatchedFieldType() {
        EqualsMismatchFieldType equals = new EqualsMismatchFieldType();

        assertThatThrownBy(() -> validator.validate(equals))
                .isInstanceOf(ValidationException.class)
                .rootCause()
                .isInstanceOf(InvalidFieldType.class)
                .hasMessage(messageSourceProvider.get("exception.InvalidFieldType.MismatchTypes.message"));
    }
}
