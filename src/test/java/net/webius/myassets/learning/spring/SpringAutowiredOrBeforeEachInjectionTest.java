package net.webius.myassets.learning.spring;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.annotation.EnableMatchConstraint;
import net.webius.myassets.annotation.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("@Autowired 또는 @BeforeEach 의존성 주입 테스트")
public class SpringAutowiredOrBeforeEachInjectionTest {
    private static final Logger log = LoggerFactory.getLogger(SpringAutowiredOrBeforeEachInjectionTest.class);
    private final Validator validator;
    private Validator validatorBeforeEach;

    @Autowired
    public SpringAutowiredOrBeforeEachInjectionTest(Validator validator) {
        this.validator = validator;
    }

    @BeforeEach
    public void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            this.validatorBeforeEach = factory.getValidator();
        }
    }

    @Test @DisplayName("Validation 메시지 인터폴레이션 비교")
    public void injection() {
        MatchTest matchTest = new MatchTest("match", "mismatch");

        Set<ConstraintViolation<MatchTest>> violations;
        ConstraintViolation<MatchTest> violation;

        // @Autowired
        violations = validator.validate(matchTest);
        violation = violations.iterator().next();
        log.info("@Autowired: {}", violation.getMessage());
        assertThat(violation.getMessage()).isEqualTo("mismatch fields.");

        // @BeforeEach
        violations = validatorBeforeEach.validate(matchTest);
        violation = violations.iterator().next();
        log.info("@BeforeEach: {}", violation.getMessage());
        assertThat(violation.getMessage()).isEqualTo("{validation.constraints.EnableMatchConstraint.message}");
    }

    @EnableMatchConstraint
    @AllArgsConstructor @Getter @Setter
    private static class MatchTest {
        private String field1;
        @Match("field1")
        private String field2;
    }

    /* @Autowired 주입 시
2024-11-18T22:00:41.586+09:00  INFO 25096 --- [my-assets] [    Test worker] n.w.m.validator.MatchValidatorTest       : [ConstraintViolationImpl{interpolatedMessage='mismatch fields.', propertyPath=, rootBeanClass=class net.webius.myassets.validator.MatchValidatorTest$PasswordMatch, messageTemplate='{validation.constraints.EnableMatchConstraint.message}'}]
2024-11-18T22:00:41.586+09:00  INFO 25096 --- [my-assets] [    Test worker] n.w.m.validator.MatchValidatorTest       : ConstraintViolationImpl{interpolatedMessage='mismatch fields.', propertyPath=, rootBeanClass=class net.webius.myassets.validator.MatchValidatorTest$PasswordMatch, messageTemplate='{validation.constraints.EnableMatchConstraint.message}'}
2024-11-18T22:00:41.586+09:00  INFO 25096 --- [my-assets] [    Test worker] n.w.m.validator.MatchValidatorTest       : mismatch fields.
     */

    /* @BeforeEach 초기화 시
//2024-11-18T21:59:50.249+09:00  INFO 26800 --- [my-assets] [    Test worker] n.w.m.validator.MatchValidatorTest       : [ConstraintViolationImpl{interpolatedMessage='{validation.constraints.EnableMatchConstraint.message}', propertyPath=, rootBeanClass=class net.webius.myassets.validator.MatchValidatorTest$PasswordMatch, messageTemplate='{validation.constraints.EnableMatchConstraint.message}'}]
//2024-11-18T21:59:50.249+09:00  INFO 26800 --- [my-assets] [    Test worker] n.w.m.validator.MatchValidatorTest       : ConstraintViolationImpl{interpolatedMessage='{validation.constraints.EnableMatchConstraint.message}', propertyPath=, rootBeanClass=class net.webius.myassets.validator.MatchValidatorTest$PasswordMatch, messageTemplate='{validation.constraints.EnableMatchConstraint.message}'}
//2024-11-18T21:59:50.249+09:00  INFO 26800 --- [my-assets] [    Test worker] n.w.m.validator.MatchValidatorTest       : {validation.constraints.EnableMatchConstraint.message}
     */
}
