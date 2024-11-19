package net.webius.myassets.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.webius.myassets.annotation.Password;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final String PASSWORD_MATCH_REGEX = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[~!@#$%^&*()\\-_=+])[a-zA-Z\\d~!@#$%^&*()\\-_=+]*$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = true;

        context.disableDefaultConstraintViolation();

        String trimmedValue = value.trim();

        if (trimmedValue.contains(" ")) {
            isValid = false;

            context
                    .buildConstraintViolationWithTemplate("{validation.constraints.Password.Space.message}")
                    .addConstraintViolation();
        }

        if (trimmedValue.length() < 8 || trimmedValue.length() > 20) {
            isValid = false;

            context
                    .buildConstraintViolationWithTemplate("{validation.constraints.Password.Size.message}")
                    .addConstraintViolation();
        }

        if (!trimmedValue.matches(PASSWORD_MATCH_REGEX)) {
            isValid = false;

            context
                    .buildConstraintViolationWithTemplate("{validation.constraints.Password.Contains.message}")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
