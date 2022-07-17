package com.kakura.libraryproject.model.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final String POOL_PROPERTY_FILE = "pool";
    private static final String POOL_SIZE_PROPERTY = "size";
    private static final int DEFAULT_POOL_SIZE = 16;
    private static final int POOL_SIZE;
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final Lock createInstanceLock = new ReentrantLock(true);
    private static BlockingDeque<ProxyConnection> freeConnections;
    private static BlockingDeque<ProxyConnection> busyConnections;
    private static ConnectionPool instance;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(POOL_PROPERTY_FILE);
        String poolSize;
        if (resourceBundle.containsKey(POOL_SIZE_PROPERTY)) {
            poolSize = resourceBundle.getString(POOL_SIZE_PROPERTY);
            POOL_SIZE = Integer.parseInt(poolSize);
        } else {
            logger.warn("Pool size will be initialised by a default value");
            POOL_SIZE = DEFAULT_POOL_SIZE;
        }
    }

    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        busyConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionFactory.getConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            } catch (SQLException e) {
                logger.error("Connection was not created", e);
            }
        }
        if (freeConnections.isEmpty()) {
            logger.fatal("Connection pool was not created: no connections were created");
            throw new RuntimeException("Connection pool was not created: no connections were created");
        }

        if (freeConnections.size() < POOL_SIZE) {
            for (int i = 0; i < (POOL_SIZE - freeConnections.size()); i++) {
                try {
                    Connection connection = ConnectionFactory.getConnection();
                    ProxyConnection proxyConnection = new ProxyConnection(connection);
                    freeConnections.add(proxyConnection);
                } catch (SQLException e) {
                    logger.fatal("Not enough connections were created");
                    throw new RuntimeException("Not enough connections were created");
                }
            }
        }


    }


    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            try {
                createInstanceLock.lock();
                if (isCreated.compareAndSet(false, true)) {
                    instance = new ConnectionPool();
                }
            } finally {
                createInstanceLock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            busyConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error("Error has occurred while getting connection", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            try {
                if (busyConnections.remove(connection)) {
                    freeConnections.put((ProxyConnection) connection);
                }
            } catch (InterruptedException e) {
                logger.error("Unable to release connection", e);
                Thread.currentThread().interrupt();
            }
        } else {
            logger.fatal("Unknown connection");
            throw new RuntimeException("Unknown connection");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (SQLException e) {
                logger.error("Unable to close connection", e);
            } catch (InterruptedException e) {
                logger.error("Thread was interrupted while taking free connection", e);
            }
        }
        logger.info("Connection pool was destroyed");
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Unable to deregister driver");
            }
        });
    }
}
