package com.epam.uber.dao;

import com.epam.uber.entity.user.Entity;
import com.epam.uber.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> implements DAO<T> {

    public static final String ID_COLUMN_LABEL = "id";

    private static final String INSERT_QUERY_KEY = "INSERT INTO %s %s VALUES %s";
    private static final String DELETE_BY_ID_QUERY_KEY = "DELETE FROM %s WHERE id= ?";

    private Connection connection;
    private final String tableName;


    public AbstractDAO(Connection connection, String tableName) {
        this.tableName = tableName;
        this.connection = connection;
    }


    public abstract List<String> getEntityParameters(T entity);

    public abstract T buildEntity(ResultSet result) throws DAOException;

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

    public boolean executeQuery(String sqlQuery, List<String> parameters) throws DAOException {
        try (PreparedStatement preparedStatement = preparedStatementForQuery(sqlQuery, parameters)) {
            int queryResult = preparedStatement.executeUpdate();
            return queryResult != 0;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    private PreparedStatement preparedStatementForQuery(String sqlQuery, List<String> parameters) throws DAOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            if (parameters != null) {
                int parameterIndex = 1;
                for (String parameter : parameters) {
                    if ("true false".contains(parameter)) {
                        boolean bool = Boolean.parseBoolean(parameter);
                        preparedStatement.setBoolean(parameterIndex, bool);
                    } else {
                        preparedStatement.setString(parameterIndex, parameter);
                    }
                    parameterIndex++;
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

    public T getEntity(String sqlQuery, List<String> params) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementForQuery(sqlQuery, params);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildEntity(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public List<T> getEntities(String sqlQuery) throws DAOException {
        try {
            Statement statement = connection.createStatement();
            List<T> entities = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                T entity = buildEntity(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteById(int id) throws DAOException {
        List<String> params = Collections.singletonList(String.valueOf(id));
        String sqlQuery = String.format(DELETE_BY_ID_QUERY_KEY, tableName);
        return executeQuery(sqlQuery, params);
    }

    @Override
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
