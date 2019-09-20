package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.Tariff;
import com.epam.uber.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TariffDAOImpl extends AbstractDAO<Tariff> {

    private Connection connection;

    public TariffDAOImpl(Connection connection) {
        super(connection, "tariff");
        this.connection = connection;
    }

    public Tariff getTariffById(int id) throws DAOException {
        String sqlQuery = "SELECT * FROM tariff WHERE id=?";
        List<String> params = Collections.singletonList(String.valueOf(id));
        return getEntity(sqlQuery, params);
    }

    public boolean updateTariff(Tariff tariff) throws DAOException {
        String sqlQuery = "UPDATE tariff SET start_time=? rate=? WHERE id=?";
        return update(tariff, sqlQuery);
    }

    public Tariff getCurrTariff() throws DAOException {
        String sqlQuery = "SELECT * FROM tariff ORDER BY id DESC LIMIT 1";
        return getRate(sqlQuery);
    }

    private Tariff getRate(String sqlQuery) throws DAOException {
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                return buildEntity(resultSet);
            }
            return new Tariff();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }

    }

    public int insertTariff(Tariff tariff) throws DAOException {
        String fields = "(start_time, rate)";
        return insert(tariff, fields);
    }

    @Override
    public List<String> getEntityParameters(Tariff entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");

        String start = entity.getStart().format(formatter);
        String rate = String.valueOf(entity.getRate());

        return new ArrayList<>(Arrays.asList(start, rate));
    }

    @Override
    public Tariff buildEntity(ResultSet result) throws DAOException {
        try {
            int id = result.getInt(ID_COLUMN_LABEL);
            Date date = result.getDate("start_time");
            LocalDateTime startDate = convertToLocalDateTime(date);
            double rate = result.getDouble("rate");

            return new Tariff(id, startDate, rate);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }


}
