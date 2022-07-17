package com.kakura.libraryproject.model.dao;

import com.kakura.libraryproject.entity.AbstractEntity;
import com.kakura.libraryproject.exception.DaoException;
import com.kakura.libraryproject.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class BaseDao<K, T extends AbstractEntity> {

    protected int ZERO_INDEX = 0;
    protected int FIRST_PARAM_INDEX = 1;
    protected int SECOND_PARAM_INDEX = 2;
    protected int THIRD_PARAM_INDEX = 3;
    protected int FOURTH_PARAM_INDEX = 4;
    protected int FIFTH_PARAM_INDEX = 5;
    protected int SIXTH_PARAM_INDEX = 6;
    protected int SEVENTH_PARAM_INDEX = 7;
    protected int EIGHT_PARAM_INDEX = 8;
    protected int NINE_PARAM_INDEX = 9;

    protected Logger logger = LogManager.getLogger();

    protected Connection connection;

    abstract public long add(T t) throws DaoException;

    abstract public boolean update(T t) throws DaoException;

    abstract public boolean delete(K k) throws DaoException;

    abstract public List<T> findAll() throws DaoException;

    abstract public Optional<T> findById(K k) throws DaoException;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeConnection() {
        if (connection != null) {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

}
