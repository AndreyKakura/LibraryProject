package com.kakura.libraryproject.model.service.impl;

import com.kakura.libraryproject.model.dao.DaoProvider;
import com.kakura.libraryproject.model.dao.UserDao;
import com.kakura.libraryproject.entity.User;
import com.kakura.libraryproject.exception.DaoException;
import com.kakura.libraryproject.exception.ServiceException;
import com.kakura.libraryproject.model.service.UserService;
import com.kakura.libraryproject.util.PasswordEncoder;
import com.kakura.libraryproject.validator.UserValidator;
import com.kakura.libraryproject.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final DaoProvider daoProvider = DaoProvider.getInstance();

    private static final UserService instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) throws ServiceException {
        UserDao userDao = daoProvider.getUserDao(false);
        //validate login, pass + md5 //todo
        boolean match = false;
        try {
            match = userDao.authenticate(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }

    @Override
    public Optional<User> findUser(String login, String password) throws ServiceException {
        UserDao userDao = daoProvider.getUserDao(false);
        UserValidator userValidator = UserValidatorImpl.getInstance();
        try {
            // if(userValidator.isLoginValid(login) && userValidator.isPasswordValid(password)) { //todo
            Optional<User> user = userDao.findUserByLogin(login);
            Optional<String> userPassword = userDao.findUserPassword(login);
            //Optional<String> encodedPassword = PasswordEncoder.encode(password); //todo
            Optional<String> encodedPassword = Optional.of(password);
            if (user.isPresent() && userPassword.isPresent() && encodedPassword.isPresent()
                    && userPassword.get().equals(encodedPassword.get())) {
                return user;
            }
            //}
        } catch (DaoException e) {
            logger.error("Error has occurred while searching for user with login \"{}\": {}", login, e);
            throw new ServiceException("Error has occurred while searching for user with login \"" + login + "\": ", e);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }
}
