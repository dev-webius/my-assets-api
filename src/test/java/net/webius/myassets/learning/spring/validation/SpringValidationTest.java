package net.webius.myassets.learning.spring.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("Validation 학습 테스트")
public class SpringValidationTest {
    private static final Logger log = LoggerFactory.getLogger(SpringValidationTest.class);

    private final Validator validator;

    @Autowired
    public SpringValidationTest(Validator validator) {
        this.validator = validator;
    }

    @Test @DisplayName("NotNull 테스트")
    public void notNull() {
        NotNullTest notNullTest = new NotNullTest("test");
        NotNullTest nullTest = new NotNullTest(null);

        /* [] */
        Set<ConstraintViolation<NotNullTest>> violations = validator.validate(notNullTest);
        assertThat(violations).isEmpty();

        /*
        [
            ConstraintViolationImpl {
                interpolatedMessage='널이어서는 안됩니다',
                propertyPath=name,
                rootBeanClass=class net.webius.myassets.learning.spring.validation.SpringValidationTest$NotNullTest,
                messageTemplate='{jakarta.validation.constraints.NotNull.message}'
            }
        ]
         */
        violations = validator.validate(nullTest);
        ConstraintViolation<NotNullTest> nullViolation = violations.stream()
                        .filter(violation -> violation.getMessage().equals("널이어서는 안됩니다"))
                        .findFirst()
                        .orElse(null);
        assertThat(nullViolation).isNotNull();
    }

    @AllArgsConstructor @Getter @Setter
    private static class NotNullTest {
        @NotNull
        private String name;
    }
}
