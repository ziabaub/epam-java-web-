package com.epam.uber.pool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConnectionCreator {

    private static final Logger LOGGER = Logger.getLogger(ConnectionCreator.class);

    private static final String RESOURCE_BUNDLE_FILE_NAME = "database";
    private static final String POOL_SIZE_PROPERTY_KEY = "db.poolSize";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(RESOURCE_BUNDLE_FILE_NAME);

    public Deque<Connection> createPool() {
        LinkedList<Connection> pool = new LinkedList<>();
        String poolSizeValue = RESOURCE_BUNDLE.getString(POOL_SIZE_PROPERTY_KEY);
        int currentPoolSize = Integer.parseInt(poolSizeValue);

        for (int listIndex = 0; listIndex < currentPoolSize; listIndex++) {
            Connection connection = create();
            pool.addLast(connection);
        }
        LOGGER.info("Pool was created successful.");
        return pool;
    }

    private Connection create() {
        String userProperty = "user";
        String passwordProperty = "password";
        String autoReconnectProperty = "autoReconnect";
        String characterEncodingProperty = "characterEncoding";
        String unicodeProperty = "useUnicode";
        String unicodePropertyKey = "db.useUnicode";

        String characterEncodingPropertyKey = "db.encoding";
        String autoReconnectPropertyKey = "db.autoReconnect";
        String passwordPropertyKey = "db.password";
        String userPropertyKey = "db.user";
        String urlPropertyKey = "db.url";
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            LOGGER.info("Driver was registered successful.");
        } catch (SQLException exception) {
            LOGGER.warn("SQL exception was detected during driver registration.");
            throw new ExceptionInInitializerError("Driver hasn't been registered. " + exception.getMessage());
        }

        String connectionUrlValue = RESOURCE_BUNDLE.getString(urlPropertyKey);
        String userValue = RESOURCE_BUNDLE.getString(userPropertyKey);
        String passwordValue = RESOURCE_BUNDLE.getString(passwordPropertyKey);
        String autoReconnectValue = RESOURCE_BUNDLE.getString(autoReconnectPropertyKey);
        String characterEncodingValue = RESOURCE_BUNDLE.getString(characterEncodingPropertyKey);
        String unicodeValue = RESOURCE_BUNDLE.getString(unicodePropertyKey);

        Properties properties = new Properties();
        properties.put(userProperty, userValue);
        properties.put(passwordProperty, passwordValue);
        properties.put(autoReconnectProperty, autoReconnectValue);
        properties.put(characterEncodingProperty, characterEncodingValue);
        properties.put(unicodeProperty, unicodeValue);

        try {
            Connection connection = DriverManager.getConnection(connectionUrlValue, properties);
            LOGGER.info("Connection was created successful.");
            return connection;
        } catch (SQLException exception) {
            LOGGER.warn("SQL exception was detected during connection creating.");
            throw new ExceptionInInitializerError("Connection hasn't been created. " + exception.getMessage());
        }
    }

}
