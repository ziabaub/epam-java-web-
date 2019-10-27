package com.epam.uber.pool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@SuppressWarnings("SpellCheckingInspection")
public class ConnectionPool {

    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private static final AtomicBoolean instanceIsNotAvailable = new AtomicBoolean(true);
    private static final Lock instanceLocker = new ReentrantLock();
    private static final Lock poolLocker = new ReentrantLock();
    private static ConnectionPool instance;

    private final Deque<Connection> pool;

    private ConnectionPool() {
        ConnectionCreator connectionCreator = new ConnectionCreator();
        pool = connectionCreator.createPool();
    }

    public static ConnectionPool getInstance() {

        if (instanceIsNotAvailable.get()) {
            instanceLocker.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceIsNotAvailable.set(false);
                }
            } finally {
                instanceLocker.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection;
        poolLocker.lock();
        try {
            connection = pool.poll();
        } finally {
            poolLocker.unlock();
        }

        return connection;
    }

    public void returnConnection(Connection connection) {
        poolLocker.lock();
        try {
            pool.addLast(connection);
        } finally {
            poolLocker.unlock();
        }
    }

    public void closePool() {
        for (Connection connection : pool) {
            try {
                connection.close();
            } catch (SQLException exception) {
                LOGGER.error("Exception was detected during pool closing.", exception);
            }
        }
    }
}
