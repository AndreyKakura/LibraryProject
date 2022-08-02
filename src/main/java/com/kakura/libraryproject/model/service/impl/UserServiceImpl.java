package com.kakura.libraryproject.model.service.impl;

import com.kakura.libraryproject.controller.command.impl.SignOutCommand;
import com.kakura.libraryproject.entity.UserRole;
import com.kakura.libraryproject.model.dao.DaoProvider;
import com.kakura.libraryproject.model.dao.Transaction;
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

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.kakura.libraryproject.controller.command.RequestParameter.*;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService instance = new UserServiceImpl();
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String PLUS_SYMBOL = "+";
    private static final String EMPTY_SYMBOL = "";

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
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
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        UserValidator userValidator = UserValidatorImpl.getInstance();
        try {
            if (userValidator.isLoginValid(login) && userValidator.isPasswordValid(password)) {
                Optional<User> user = userDao.findUserByLogin(login);
                Optional<String> userPassword = userDao.findUserPassword(login);
                Optional<String> encodedPassword = PasswordEncoder.encode(password);
                if (user.isPresent() && userPassword.isPresent() && encodedPassword.isPresent()
                        && userPassword.get().equals(encodedPassword.get())) {
                    return user;
                }
            }
        } catch (DaoException e) {
            logger.error("Error has occurred while searching for user with login \"{}\": {}", login, e);
            throw new ServiceException("Error has occurred while searching for user with login \"" + login + "\": ", e);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String login) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        try {
            Optional<User> user = userDao.findUserByLogin(login);
            if (user.isPresent()) {
                return user;
            }
        } catch (DaoException e) {
            logger.error("Error has occurred while finding user by login: " + e);
            throw new ServiceException("Error has occurred while finding user by login: ", e);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(Long id) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        try {
            Optional<User> user = userDao.findById(id);
            if (user.isPresent()) {
                return user;
            }
        } catch (DaoException e) {
            logger.error("Error has occurred while finding user by id: " + e);
            throw new ServiceException("Error has occurred while finding user by id: ", e);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findUsers() throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        try {
            List<User> users = userDao.findAll();
            return users;
        } catch (DaoException e) {
            logger.error("Error has occurred while finding users: " + e);
            throw new ServiceException("Error has occurred while finding users: ", e);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public boolean isLoginOccupied(String login) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        try {
            Optional<User> optionalUser = userDao.findUserByLogin(login);
            return optionalUser.isPresent();
        } catch (DaoException e) {
            logger.error("Error has occurred while checking login availability: " + e);
            throw new ServiceException("Error has occurred while checking login availability: ", e);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public boolean isEmailOccupied(String email) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        try {
            Optional<User> optionalUser = userDao.findUserByEmail(email);
            return optionalUser.isPresent();
        } catch (DaoException e) {
            logger.error("Error has occurred while checking email availability: " + e);
            throw new ServiceException("Error has occurred while checking email availability: ", e);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public boolean arePasswordsEqual(String password, String repeatedPassword) throws ServiceException {
        return password != null && password.equals(repeatedPassword);
    }

    @Override
    public boolean registerUser(Map<String, String> userData) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(true);
        UserValidator validator = UserValidatorImpl.getInstance();
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(userDao);
            if (validator.isUserDataValid(userData)) {
                if (!userData.get(PASSWORD).equals(userData.get(REPEATED_PASSWORD))) {
                    userData.put(REPEATED_PASSWORD, INCORRECT_VALUE_PARAMETER);
                    return false;
                }
                Optional<String> password = PasswordEncoder.encode(userData.get(PASSWORD));
                if (password.isPresent()) {
                    //transaction begin was here
                    BigInteger phoneNumber = new BigInteger(userData.get(PHONE).replace(PLUS_SYMBOL, EMPTY_SYMBOL));
                    User user = new User.UserBuilder()
                            .setLogin(userData.get(LOGIN))
                            .setSurname(userData.get(SURNAME))
                            .setName(userData.get(NAME))
                            .setEmail(userData.get(EMAIL))
                            .setPhone(phoneNumber)
                            .setUserRole(UserRole.USER)
                            .build();
                    userDao.add(user);
                    userDao.updateUserPassword(password.get(), userData.get(LOGIN));
                    transaction.commit();
                    return true;
                }
            }
            return false;
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                logger.error("Error has occurred while doing transaction rollback for registering user: " + daoException);
            }
            logger.error("Error has occurred while registering user: " + e);
            throw new ServiceException("Error has occurred while registering user: " + e);
        } finally {
            try {
                if (transaction != null) {
                    transaction.end();
                }
            } catch (DaoException e) {
                logger.error("Error has occurred while ending transaction for registering user: " + e);
            }
        }
    }

    @Override
    public boolean updateUserData(Map<String, String> userData) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        UserValidator validator = UserValidatorImpl.getInstance();

        try {
            if (validator.isUserDataValid(userData)) {
                Optional<String> password;

                password = PasswordEncoder.encode(userData.get(PASSWORD));
                if (password.isPresent()) {
                    BigInteger phoneNumber = new BigInteger(userData.get(PHONE).replace(PLUS_SYMBOL, EMPTY_SYMBOL));
                    User user = new User.UserBuilder()
                            .setLogin(userData.get(LOGIN))
                            .setSurname(userData.get(SURNAME))
                            .setName(userData.get(NAME))
                            .setEmail(userData.get(EMAIL))
                            .setPhone(phoneNumber)
                            .setId(Long.parseLong(userData.get(ID)))
                            .build();
                    userDao.update(user);
                    return true;
                }
            }
            return false;
        } catch (DaoException e) {
            logger.error("Error has occurred while updating user account: " + e);
            throw new ServiceException("Error has occurred while updating user account: ", e);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public boolean updatePassword(Map<String, String> userData) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        UserValidator validator = UserValidatorImpl.getInstance();
        try {
            if (validator.isPasswordValid(userData.get(NEW_PASSWORD))) {
                Optional<String> password;

                password = PasswordEncoder.encode(userData.get(NEW_PASSWORD));
                System.out.println(userData.get(NEW_PASSWORD));
                if (password.isPresent()) {
                    userDao.updateUserPassword(password.get(), userData.get(LOGIN));
                    return true;
                }

            }
            return false;
        } catch (DaoException e) {
            logger.error("Error has occurred while updating user password: " + e);
            throw new ServiceException("Error has occurred while updating user password: ", e);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public boolean updateUserStatusAndRole(Map<String, String> userData) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        try {
            String status = userData.get(STATUS);
            String role = userData.get(ROLE);
            Long id = Long.parseLong(userData.get(ID));
            return userDao.updateUserStatusRole(status, role, id);

        } catch (DaoException e) {
            logger.error("Error has occurred while updating user status and role: " + e);
            throw new ServiceException("Error has occurred while updating user status and role: ", e);
        } finally {
            userDao.closeConnection();
        }
    }
}
