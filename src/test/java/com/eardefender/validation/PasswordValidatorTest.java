package com.eardefender.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class PasswordValidatorTest {

    private PasswordValidator passwordValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        passwordValidator = new PasswordValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"P@ssw0rd", "C0rr3cTPASS!!", "V4LiDP4ass?"})
    void isValid_ValidPassword_ReturnsTrue(String password) {
        assertTrue(passwordValidator.isValid(password, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {"P@1s!", "pass0O!", "P0lAnD!"})
    void isValid_PasswordIsTooShort_ReturnsFalse(String password) {
        assertFalse(passwordValidator.isValid(password, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {"784EWUIHOUWFHHUFIASasddhjasklh!!fsIYUFJKVFJHHJGSDKAFSDFKGHJDSFKSGHJKHSGQWEQW123ADSAAFSS"})
    void isValid_PasswordIsTooLong_ReturnsFalse(String password) {
        assertFalse(passwordValidator.isValid(password, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {"P@ssword!", "PolandWithoutDIGIT!", "HelloWorld?"})
    void isValid_PasswordDoesNotHaveDigit_ReturnsFalse(String password) {
        assertFalse(passwordValidator.isValid(password, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {"p1@ssword!", "polandwithoutuppercase1!", "333higuyswhatsup?"})
    void isValid_PasswordDoesNotHaveUpperCaseCharacter_ReturnsFalse(String password) {
        assertFalse(passwordValidator.isValid(password, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {"PASSWORD!!!1234", "POLANDWITHOUTLOWERCASE123!", "UWUOWOESSA1234?"})
    void isValid_PasswordDoesNotHaveLowerCaseCharacter_ReturnsFalse(String password) {
        assertFalse(passwordValidator.isValid(password, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {"PASSWORD1234aaa", "POLANDWITHOUTspecialchar123", "anotherLOVEanotherLove505"})
    void isValid_PasswordDoesNotHaveSpecialCharacter_ReturnsFalse(String password) {
        assertFalse(passwordValidator.isValid(password, context));
    }
}