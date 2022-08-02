package com.kakura.libraryproject.validator.impl;

import com.kakura.libraryproject.validator.UserValidator;

import java.util.Map;

import static com.kakura.libraryproject.controller.command.RequestParameter.*;

public class UserValidatorImpl implements UserValidator {
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String LOGIN_REGEX = "[\\p{Alpha}][\\p{Alpha}\\d]{3,19}";
    private static final String PASSWORD_REGEX = "[\\p{Alpha}][\\p{Alpha}\\d]{7,29}";
    private static final String SURNAME_REGEX = "[А-Я\\p{Upper}][а-я\\p{Lower}]{1,19}";
    private static final String NAME_REGEX = "[А-Я\\p{Upper}][а-яё\\p{Lower}]{1,19}";
    private static final String EMAIL_REGEX = "(([\\p{Alpha}\\d._]+){5,25}@([\\p{Lower}]+){3,7}\\.([\\p{Lower}]+){2,3})";
    private static final String NUMBER_REGEX = "\\+375(25|29|33|44)\\d{7}";
    private static final UserValidator instance = new UserValidatorImpl();

    private UserValidatorImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserValidator getInstance() {
        return instance;
    }

    @Override
    public boolean isLoginValid(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }

    @Override
    public boolean isPasswordValid(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    @Override
    public boolean isSurnameValid(String surname) {
        return surname != null && surname.matches(SURNAME_REGEX);
    }

    @Override
    public boolean isNameValid(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    @Override
    public boolean isEmailValid(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean isNumberValid(String number) {
        return number != null && number.matches(NUMBER_REGEX);
    }

    @Override
    public boolean isUserDataValid(Map<String, String> userData) {
        if(!isLoginValid(userData.get(LOGIN))) {
            userData.put(LOGIN, userData.get(LOGIN) + INCORRECT_VALUE_PARAMETER);
            return false;
        }
        if(!isPasswordValid(userData.get(PASSWORD))) {
            userData.put(PASSWORD, userData.get(PASSWORD) + INCORRECT_VALUE_PARAMETER);
            return false;
        }
        if (!isPasswordValid(userData.get(PASSWORD))) {
            userData.put(PASSWORD, userData.get(LOGIN) + INCORRECT_VALUE_PARAMETER);
            return false;
        }
        if (!isSurnameValid(userData.get(SURNAME))) {
            userData.put(SURNAME, userData.get(SURNAME) + INCORRECT_VALUE_PARAMETER);
            return false;
        }
        if (!isNameValid(userData.get(NAME))) {
            userData.put(NAME, userData.get(NAME) + INCORRECT_VALUE_PARAMETER);
            return false;
        }
        if (!isEmailValid(userData.get(EMAIL))) {
            userData.put(EMAIL, userData.get(EMAIL) + INCORRECT_VALUE_PARAMETER);
            return false;
        }
        if (!isNumberValid(userData.get(PHONE))) {
            userData.put(PHONE, userData.get(PHONE) + INCORRECT_VALUE_PARAMETER);
            return false;
        }
        return true;
    }
}
