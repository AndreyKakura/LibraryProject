package com.kakura.libraryproject.model.dao;

import com.kakura.libraryproject.exception.DaoException;
import com.kakura.libraryproject.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {
    private static final Logger logger = LogManager.getLogger();
    private static final Transaction instance = new Transaction();
    private Connection connection;

    private Transaction() {
    }

    public static Transaction getInstance() {
        return instance;
    }

    public void begin(BaseDao... daos) throws DaoException {
        if (connection == null) {
            connection = ConnectionPool.getInstance().getConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Error has occurred while beginning a transaction: " + e);
            throw new DaoException("Error has occurred while beginning a transaction: ", e);
        }
        for (BaseDao dao : daos) {
            dao.setConnection(connection);
        }
    }

    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("Error has occurred while doing transaction commit: " + e);
            throw new DaoException("Error has occurred while doing transaction commit: ", e);
        }
    }

    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("Error has occurred while doing transaction rollback: " + e);
            throw new DaoException("Error has occurred while doing transaction rollback: ", e);
        }
    }

    public void end() throws DaoException {
        try {
            connection.close();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Error has occurred while ending transaction: " + e);
            throw new DaoException("Error has occurred while ending transaction: ", e);
        }
        connection = null;
    }


}
