package com.epam.uber.dao;

import com.epam.uber.entity.Entity;
import com.epam.uber.exceptions.DAOException;

import java.sql.*;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> implements DAO<T> {

    public static final String ID_COLUMN_LABEL = "id";
    private static final String INSERT_QUERY_KEY = "INSERT INTO %s %s VALUES %s";
    private final Connection connection;
    private final String tableName;


    public AbstractDAO(Connection connection, String tableName) {
        this.tableName = tableName;
        this.connection = connection;
    }


    protected abstract List<String> getEntityParameters(T entity);

    protected abstract T buildEntity(ResultSet result) throws DAOException;

    private int getLastInsertId() throws DAOException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT last_insert_id()");
            if (resultSet.next()) {
                return resultSet.getInt("last_insert_id()");
            }
            return 0;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    protected boolean executeQuery(String sqlQuery, List<String> parameters) throws DAOException {
        try (PreparedStatement preparedStatement = buildStatement(sqlQuery, parameters)) {
            int queryResult = preparedStatement.executeUpdate();
            return queryResult != 0;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    private PreparedStatement buildStatement(String sqlQuery, List<String> parameters) throws DAOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            if (parameters != null) {
                int parameterIndex = 1;
                for (String parameter : parameters) {
                    preparedStatement.setObject(parameterIndex++, parameter);
                }
            }
            return preparedStatement;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    private String generateValuesCount(int len) {
        StringBuilder sb = new StringBuilder("( ");
        for (int i = 0; i < len; i++) {
            if (i < len - 1) {
                sb.append("?, ");
            } else {
                sb.append("? ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    protected T getEntity(String sqlQuery, List<String> params) throws DAOException {
        try {
            PreparedStatement preparedStatement = buildStatement(sqlQuery, params);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildEntity(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override //todio protected call prepareVakues
    public Integer insert(T entity, String fields) throws DAOException {
        List<String> params = getEntityParameters(entity);
        String paramsCount = generateValuesCount(params.size());
        String sqlQuery = String.format(INSERT_QUERY_KEY, tableName, fields, paramsCount);
        boolean bool = executeQuery(sqlQuery, params);
        return (bool) ? getLastInsertId() : null;
    }

    @Override
    public boolean update(T entity, String sqlQuery) throws DAOException {
        List<String> params = getEntityParameters(entity);
        int entityId = entity.getId();
        String id = String.valueOf(entityId);
        params.add(id);
        return executeQuery(sqlQuery, params);
    }

}
