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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TariffDAOImpl extends AbstractDAO<Tariff> {


    public Tariff getCurrTariff() throws DAOException {
        String sqlQuery = "SELECT * FROM tariff ORDER BY id DESC LIMIT 1";
        return getRate(sqlQuery);
    }

    private Tariff getRate(String sqlQuery) throws DAOException {
        try {
            Connection connection = getConnection();
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
        String fields = "insert into tariff (start_time, rate) values(?,?)";
        insert(tariff, fields);
    }

    public List<Tariff> selectAllTariff() throws DAOException {
        String sqlQuery = "select * from tariff ";
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            List<Tariff> tariffs = new ArrayList<>();
            while (result.next()) {
                Tariff tariff = buildEntity(result);
                tariffs.add(tariff);
            }
            return tariffs;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    protected List<String> getEntityParameters(Tariff entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");

        String start = entity.getStart().format(formatter);
        String rate = String.valueOf(entity.getRate());

        return Arrays.asList(start, rate);
    }

    @Override
    protected Tariff buildEntity(ResultSet result) throws DAOException {
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
