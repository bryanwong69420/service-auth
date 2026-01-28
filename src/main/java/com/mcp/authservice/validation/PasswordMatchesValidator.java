package com.mcp.authservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordMatchesValidator
        implements ConstraintValidator<ValidPassword, Object> {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$");

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // let @NotNull handle it
        }

        try {
            String password = (String) value.getClass()
                    .getDeclaredField("password")
                    .get(value);

            String matchingPassword = (String) value.getClass()
                    .getDeclaredField("matchingPassword")
                    .get(value);

            if (password == null || matchingPassword == null) {
                return false;
            }

            boolean matches = password.equals(matchingPassword);
            boolean strong = PASSWORD_PATTERN.matcher(password).matches();

            if (!matches || !strong) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("password")
                        .addConstraintViolation();
                return false;
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
