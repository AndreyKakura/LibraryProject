package com.kakura.libraryproject.validator;

public interface UserValidator {
    boolean isLoginValid(String login);

    boolean isPasswordValid(String password);

    boolean isSurnameValid(String surname);

    boolean isNameValid(String name);

    boolean isEmailValid(String email);

    boolean isNumberValid(String number);

    //boolean checkUserData(Map<String, String> userData);//todo

    //boolean checkUserPersonalData(Map<String, String> userData);//todo
}
