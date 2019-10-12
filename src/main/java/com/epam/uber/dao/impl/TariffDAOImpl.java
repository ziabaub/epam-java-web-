package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.order.Tariff;
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

    private final Connection connection;

    public TariffDAOImpl(Connection connection) {
        super(connection, "tariff");
        this.connection = connection;
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

    public void insertTariff(Tariff tariff) throws DAOException {
        String fields = "(start_time, rate)";
        insert(tariff, fields);
    }

    public List<Tariff> selectAllTariff()throws DAOException  {
        String sqlQuery = "select * from tariff ";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            List<Tariff> tariffs = new ArrayList<>();
            while (result.next()){
                Tariff tariff = buildEntity(result);
                tariffs.add(tariff);
            }
            return tariffs;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
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

    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
