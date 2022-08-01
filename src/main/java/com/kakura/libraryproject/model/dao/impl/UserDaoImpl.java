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
    private static final String SQL_INSERT_USER =
            "INSERT INTO users(login, surname, name, email, phone_number, role) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER =
            "UPDATE users SET login = ?, surname = ?, name = ?, email = ?, phone_number = ? WHERE login = ?";
    private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE users SET password = ? WHERE login = ?";
    private static final String SELECT_LOGIN_PASSWORD = "SELECT password FROM users WHERE lastname = ?";
    private static final String SQL_SELECT_USERS_BY_LOGIN =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM librarydb.users WHERE login = ?";
    private static final String SQL_SELECT_USERS_BY_EMAIL =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM librarydb.users WHERE email = ?";
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

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return match;
    }

    @Override
    public boolean updateUserPassword(String password, String login) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {
            preparedStatement.setString(FIRST_PARAM_INDEX, password);
            preparedStatement.setString(SECOND_PARAM_INDEX, login);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error has occurred while updating user password: " + e);
            throw new DaoException("Error has occurred while updating user password: ", e);
        }
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
    public Optional<User> findUserByEmail(String email) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_EMAIL)) {
            preparedStatement.setString(FIRST_PARAM_INDEX, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error has occurred while finding user by email: " + e);
            throw new DaoException("Error has occurred while finding user by email: ", e);
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(FIRST_PARAM_INDEX, user.getLogin());
            preparedStatement.setString(SECOND_PARAM_INDEX, user.getSurname());
            preparedStatement.setString(THIRD_PARAM_INDEX, user.getName());
            preparedStatement.setString(FOURTH_PARAM_INDEX, user.getEmail());
            preparedStatement.setLong(FIFTH_PARAM_INDEX, user.getPhoneNumber().longValue());
            preparedStatement.setString(SIXTH_PARAM_INDEX, user.getUserRole().getRole());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(FIRST_PARAM_INDEX);
        } catch (SQLException e) {
            logger.error("Error has occurred while adding user: " + e);
            throw new DaoException("Error has occurred while adding user", e);
        }
    }


    @Override
    public boolean update(User user) throws DaoException {
        //"UPDATE users SET login = ?, surname = ?, name = ?, email = ?, phone_number = ? WHERE id_user = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(FIRST_PARAM_INDEX, user.getLogin());
            preparedStatement.setString(SECOND_PARAM_INDEX, user.getSurname());
            preparedStatement.setString(THIRD_PARAM_INDEX, user.getName());
            preparedStatement.setString(FOURTH_PARAM_INDEX, user.getEmail());
            preparedStatement.setLong(FIFTH_PARAM_INDEX, user.getPhoneNumber().longValue());
            preparedStatement.setString(SIXTH_PARAM_INDEX, Long.toString(user.getId()));
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error has occurred while updating user's data: " + e);
            throw new DaoException("Error has occurred while updating user's data: ", e);
        }
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
