package net.webius.myassets.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import net.webius.myassets.annotation.validator.FieldEqualsConstraintValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldEqualsConstraintValidator.class)
public @interface FieldEqualsConstraint {
    String message() default "{validation.constraints.FieldEqualsConstraint.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
