package com.epam.uber.pool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

//implement Connection interface see Proxy pattern
public class ProxyConnection implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(ProxyConnection.class);
    private final Connection connection;
    private final ConnectionPool connectionPool;

    public ProxyConnection() {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
    }

    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            LOGGER.error("Transaction start failed. ", exception);
        }
    }

    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException exception) {
            LOGGER.error("Transaction commit failed. ", exception);
        }
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            LOGGER.error("Transaction rollback failed. ", exception);
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            LOGGER.error("Transaction end failed. ", exception);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        connectionPool.returnConnection(connection);
    }
}
