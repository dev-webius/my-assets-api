package net.webius.myassets.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.webius.myassets.annotation.IdentifierPattern;

public class IdentifierPatternValidator implements ConstraintValidator<IdentifierPattern, String> {
    private static final String IDENTIFIER_MATCH_REGEX = "^[a-zA-Z\\d\\-_]*$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = true;

        context.disableDefaultConstraintViolation();

        String trimmedValue = value.trim();

        if (trimmedValue.contains(" ")) {
            isValid = false;

            context.buildConstraintViolationWithTemplate("{validation.constraints.NoSpace.message}")
                    .addConstraintViolation();
        }

        if (trimmedValue.equalsIgnoreCase("admin")) {
            isValid = false;

            context.buildConstraintViolationWithTemplate("{validation.constraints.NoAdmin.message}")
                    .addConstraintViolation();
        }

        if (!trimmedValue.matches(IDENTIFIER_MATCH_REGEX)) {
            isValid = false;

            context.buildConstraintViolationWithTemplate("{validation.constraints.IdentifierPattern.message}")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
