package com.kakura.libraryproject.model.dao.impl;

import com.kakura.libraryproject.model.dao.BaseDao;
import com.kakura.libraryproject.model.dao.UserDao;
import com.kakura.libraryproject.entity.User;
import com.kakura.libraryproject.exception.DaoException;
import com.kakura.libraryproject.model.dao.mapper.impl.UserMapper;
import com.kakura.libraryproject.model.pool.ConnectionPool;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static com.kakura.libraryproject.model.dao.ColumnName.USER_PASSWORD;

public class UserDaoImpl extends UserDao {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String SELECT_LOGIN_PASSWORD = "SELECT password FROM users WHERE lastname = ?";
    private static final String SQL_SELECT_USERS_BY_LOGIN =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM librarydb.users WHERE login = ?";
    private static final String SQL_SELECT_USER_PASSWORD = "SELECT password FROM librarydb.users WHERE login = ?";

    public UserDaoImpl() {
    }

    public UserDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {

        boolean match = false;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            String passFromDb;

            if (resultSet.next()) {
                passFromDb = resultSet.getString(1);
                match = password.equals(passFromDb);
            }

//            System.out.println("trying to release connection");
//            ConnectionPool.getInstance().releaseConnection(connection);//todo
//            System.out.println("connection released");

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return match;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_LOGIN)) {
            preparedStatement.setString(FIRST_PARAM_INDEX, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error has occurred while finding user by login: " + e);
            throw new DaoException("Error has occurred while finding user by login: ", e);
        }
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(ZERO_INDEX));
    }

    @Override
    public Optional<String> findUserPassword(String login) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_PASSWORD)) {
            preparedStatement.setString(FIRST_PARAM_INDEX, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getString(USER_PASSWORD));
                }
            }
        } catch (SQLException e) {
            logger.error("Error has occurred while finding user pasword: " + e);
            throw new DaoException("Error has occurred while finding user pasword: ", e);
        }
        return Optional.empty();
    }

    @Override
    public long add(User user) throws DaoException {
        return 0;
    }

    @Override
    public boolean update(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Long aLong) throws DaoException {
        return false;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) throws DaoException {
        return Optional.empty();
    }
}
