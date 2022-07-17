package com.kakura.libraryproject.model.service;

import com.kakura.libraryproject.entity.User;
import com.kakura.libraryproject.exception.ServiceException;

import java.util.Optional;

public interface UserService {

    boolean authenticate(String login, String password) throws ServiceException;

    Optional<User> findUser(String login, String password) throws ServiceException;
}
