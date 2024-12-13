package net.webius.myassets.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import net.webius.myassets.annotation.validator.IdentifierPatternValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdentifierPatternValidator.class)
public @interface IdentifierPattern {
    String message() default "{validation.constraints.IdentifierPattern.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
