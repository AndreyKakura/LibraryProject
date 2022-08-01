package com.kakura.libraryproject.model.dao;

import com.kakura.libraryproject.entity.User;
import com.kakura.libraryproject.exception.DaoException;

import java.util.Optional;

public abstract class UserDao extends BaseDao<Long, User> {

    public abstract boolean authenticate(String login, String password) throws DaoException;

    abstract public boolean updateUserPassword(String password, String login) throws DaoException;

    abstract public Optional<User> findUserByLogin(String login) throws DaoException;

    abstract public Optional<User> findUserByEmail(String email) throws DaoException;

    abstract public Optional<String> findUserPassword(String login) throws DaoException;

}
