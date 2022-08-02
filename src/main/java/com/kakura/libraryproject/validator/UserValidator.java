package com.kakura.libraryproject.validator;

import java.util.Map;

public interface UserValidator {
    boolean isLoginValid(String login);

    boolean isPasswordValid(String password);

    boolean isSurnameValid(String surname);

    boolean isNameValid(String name);

    boolean isEmailValid(String email);

    boolean isNumberValid(String number);

    boolean isUserDataValid(Map<String, String> userData);

}
