package net.webius.myassets.learning.spring;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import net.webius.myassets.annotation.validator.domain.PasswordEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest @DisplayName("@Autowired 또는 @BeforeEach 의존성 주입 학습 테스트")
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
        PasswordEquals equals = new PasswordEquals("match", "mismatch");

        Set<ConstraintViolation<PasswordEquals>> violations;
        ConstraintViolation<PasswordEquals> violation;

        // @Autowired
        violations = validator.validate(equals);
        violation = violations.iterator().next();
        log.info("{}", violations);
        log.info("{}", violation);
        log.info("@Autowired - {}", violation.getMessage());
        assertThat(violation.getMessageTemplate()).isEqualTo("{validation.constraints.FieldEqualsConstraint.message}");

        // @BeforeEach
        assertThatThrownBy(() -> validatorBeforeEach.validate(equals))
                .isInstanceOf(ValidationException.class)
                .rootCause()
                .isInstanceOf(NoSuchMethodException.class);
    }

    /* @Autowired 주입 시
2024-11-19T04:05:53.948+09:00  INFO 33384 --- [    Test worker] SpringAutowiredOrBeforeEachInjectionTest : [ConstraintViolationImpl{interpolatedMessage='mismatch fields.', propertyPath=passwordConfirm>password, rootBeanClass=class net.webius.myassets.validator.domain.PasswordEquals, messageTemplate='{validation.constraints.FieldEqualsConstraint.message}'}]
2024-11-19T04:05:53.948+09:00  INFO 33384 --- [    Test worker] SpringAutowiredOrBeforeEachInjectionTest : ConstraintViolationImpl{interpolatedMessage='mismatch fields.', propertyPath=passwordConfirm>password, rootBeanClass=class net.webius.myassets.validator.domain.PasswordEquals, messageTemplate='{validation.constraints.FieldEqualsConstraint.message}'}
2024-11-19T04:05:53.948+09:00  INFO 33384 --- [    Test worker] SpringAutowiredOrBeforeEachInjectionTest : @Autowired - mismatch fields.
     */

    /* @BeforeEach 초기화 시 (MessageSourceProvider 주입으로 인해 Spring DI가 아닌 경우 정상적인 Validating 을 제공할 수 없음)
HV000064: Unable to instantiate ConstraintValidator: net.webius.myassets.validator.FieldEqualsConstraintValidator.
jakarta.validation.ValidationException: HV000064: Unable to instantiate ConstraintValidator: net.webius.myassets.validator.FieldEqualsConstraintValidator.
	at org.hibernate.validator.internal.util.privilegedactions.NewInstance.run(NewInstance.java:44)
	at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorFactoryImpl.run(ConstraintValidatorFactoryImpl.java:45)
	at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorFactoryImpl.getInstance(ConstraintValidatorFactoryImpl.java:29)
	at org.hibernate.validator.internal.engine.constraintvalidation.ClassBasedValidatorDescriptor.newInstance(ClassBasedValidatorDescriptor.java:84)
	at org.hibernate.validator.internal.engine.constraintvalidation.AbstractConstraintValidatorManagerImpl.createAndInitializeValidator(AbstractConstraintValidatorManagerImpl.java:89)
	at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManagerImpl.getInitializedValidator(ConstraintValidatorManagerImpl.java:117)
	at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree.getInitializedConstraintValidator(ConstraintTree.java:136)
	at org.hibernate.validator.internal.engine.constraintvalidation.SimpleConstraintTree.validateConstraints(SimpleConstraintTree.java:58)
	at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree.validateConstraints(ConstraintTree.java:75)
	at org.hibernate.validator.internal.metadata.core.MetaConstraint.doValidateConstraint(MetaConstraint.java:130)
	at org.hibernate.validator.internal.metadata.core.MetaConstraint.validateConstraint(MetaConstraint.java:123)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validateMetaConstraint(ValidatorImpl.java:555)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForSingleDefaultGroupElement(ValidatorImpl.java:518)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForDefaultGroup(ValidatorImpl.java:488)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForCurrentGroup(ValidatorImpl.java:450)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validateInContext(ValidatorImpl.java:400)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validate(ValidatorImpl.java:172)
	at net.webius.myassets.learning.spring.SpringAutowiredOrBeforeEachInjectionTest.injection(SpringAutowiredOrBeforeEachInjectionTest.java:56)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
Caused by: java.lang.NoSuchMethodException: net.webius.myassets.validator.FieldEqualsConstraintValidator.<init>()
	at java.base/java.lang.Class.getConstructor0(Class.java:3761)
	at java.base/java.lang.Class.getConstructor(Class.java:2442)
	at org.hibernate.validator.internal.util.privilegedactions.NewInstance.run(NewInstance.java:41)
	... 20 more


net.webius.myassets.validator.FieldEqualsConstraintValidator.<init>()
java.lang.NoSuchMethodException: net.webius.myassets.validator.FieldEqualsConstraintValidator.<init>()
	at java.base/java.lang.Class.getConstructor0(Class.java:3761)
	at java.base/java.lang.Class.getConstructor(Class.java:2442)
	at org.hibernate.validator.internal.util.privilegedactions.NewInstance.run(NewInstance.java:41)
	at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorFactoryImpl.run(ConstraintValidatorFactoryImpl.java:45)
	at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorFactoryImpl.getInstance(ConstraintValidatorFactoryImpl.java:29)
	at org.hibernate.validator.internal.engine.constraintvalidation.ClassBasedValidatorDescriptor.newInstance(ClassBasedValidatorDescriptor.java:84)
	at org.hibernate.validator.internal.engine.constraintvalidation.AbstractConstraintValidatorManagerImpl.createAndInitializeValidator(AbstractConstraintValidatorManagerImpl.java:89)
	at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManagerImpl.getInitializedValidator(ConstraintValidatorManagerImpl.java:117)
	at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree.getInitializedConstraintValidator(ConstraintTree.java:136)
	at org.hibernate.validator.internal.engine.constraintvalidation.SimpleConstraintTree.validateConstraints(SimpleConstraintTree.java:58)
	at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree.validateConstraints(ConstraintTree.java:75)
	at org.hibernate.validator.internal.metadata.core.MetaConstraint.doValidateConstraint(MetaConstraint.java:130)
	at org.hibernate.validator.internal.metadata.core.MetaConstraint.validateConstraint(MetaConstraint.java:123)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validateMetaConstraint(ValidatorImpl.java:555)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForSingleDefaultGroupElement(ValidatorImpl.java:518)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForDefaultGroup(ValidatorImpl.java:488)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForCurrentGroup(ValidatorImpl.java:450)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validateInContext(ValidatorImpl.java:400)
	at org.hibernate.validator.internal.engine.ValidatorImpl.validate(ValidatorImpl.java:172)
	at net.webius.myassets.learning.spring.SpringAutowiredOrBeforeEachInjectionTest.injection(SpringAutowiredOrBeforeEachInjectionTest.java:56)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
     */
}
