package com.epam.uber.pool;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionPool {

    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private static final Lock instanceLocker;
    private static final Lock poolLocker;
    private static final Condition poolCondition;

    private static ConnectionPool instance;
    private static final AtomicBoolean instanceIsNotAvailable;

    private final Deque<Connection> pool;

    static {
        instance = null;
        instanceLocker = new ReentrantLock();
        poolLocker = new ReentrantLock();
        poolCondition = poolLocker.newCondition();
        instanceIsNotAvailable = new AtomicBoolean(true);
    }

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
        Connection connection = null ;
        poolLocker.lock();
        try {
            if (pool.isEmpty()) {
                poolCondition.await();
            }
            connection = pool.poll();
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, "Interrupted!", e);
            Thread.currentThread().interrupt();
        } finally {
            poolLocker.unlock();
        }

        return connection;
    }

    public void returnConnection(Connection connection) {
        poolLocker.lock();

        try {
            pool.addLast(connection);
            poolCondition.signal();
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
