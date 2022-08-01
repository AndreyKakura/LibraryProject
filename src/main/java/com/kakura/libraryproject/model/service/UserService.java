package com.kakura.libraryproject.model.service;

import com.kakura.libraryproject.entity.User;
import com.kakura.libraryproject.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface UserService {

    boolean authenticate(String login, String password) throws ServiceException;

    Optional<User> findUser(String login, String password) throws ServiceException;

    Optional<User> findUser(String login) throws ServiceException;

    boolean isLoginOccupied(String login) throws ServiceException;

    boolean isEmailOccupied(String email) throws ServiceException;

    boolean arePasswordsEqual(String password, String repeatedPassword) throws ServiceException;

    boolean registerUser(Map<String, String> userData) throws ServiceException;

    boolean updateUserData(Map<String, String> userData) throws ServiceException;


}
