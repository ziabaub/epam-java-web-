package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.Tariff;
import com.epam.uber.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TariffDAOImpl extends AbstractDAO<Tariff> {


    public TariffDAOImpl(Connection connection) {
        super(connection, "tariff");
    }

    public Tariff getTariffById(int id) throws DAOException {
        String sqlQuery = "SELECT * FROM tariff WHERE id=?";
        List<String> params = Collections.singletonList(String.valueOf(id));
        return getEntity(sqlQuery, params);
    }

    public boolean updateTariff(Tariff tariff) throws DAOException {
        String sqlQuery = "UPDATE tariff SET start_time=?, end_time=? rate=? WHERE id=?";
        return update(tariff, sqlQuery);
    }

    public int insertTariff(Tariff tariff) throws DAOException {
        String fields = "(start_time, end_time, rate)";
        return insert(tariff,fields);
    }

    @Override
    public List<String> getEntityParameters(Tariff entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String start = entity.getStart().format(formatter);
        String end = entity.getEnd().format(formatter);
        String rate = String.valueOf(entity.getRate());

        return new ArrayList<>(Arrays.asList(start, end, rate));
    }

    @Override
    public Tariff buildEntity(ResultSet result) throws DAOException {
        try {
            int id = result.getInt(ID_COLUMN_LABEL);
            LocalTime start = LocalTime.parse(result.getString("start_time"));
            LocalTime end = LocalTime.parse(result.getString("end_time"));
            double rate = result.getDouble("rate");

            return new Tariff(id, start, end, rate);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }


}
