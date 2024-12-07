package com.eardefender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        return isValidPassword(value);
    }

    private boolean isValidPassword(String value) {
        return isValidLength(value)
                && containsDigit(value)
                && containsUpperCaseLetter(value)
                && containsLowerCaseLetter(value)
                && containsSpecialCharacter(value);
    }

    private boolean isValidLength(String value) {
        return value.length() >= 8 && value.length() <= 60;
    }

    private boolean containsDigit(String value) {
        for (char c : value.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsUpperCaseLetter(String value) {
        for (char c : value.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsLowerCaseLetter(String value) {
        for (char c : value.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSpecialCharacter(String value) {
        String specialCharacters = "!@#$%^&*_+-=?";
        for (char c : value.toCharArray()) {
            if (specialCharacters.indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }
}