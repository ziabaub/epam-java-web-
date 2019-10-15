package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.order.Location;
import com.epam.uber.exceptions.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LocationDAOImpl extends AbstractDAO<Location> {


    public int insertLocation(Location location) throws DAOException {
        String query = "insert into location (country,city,zone) values (?,?,?)";
        return insert(location, query);
    }

    public void deleteById(int id) throws DAOException {
        List<String> params = Collections.singletonList(String.valueOf(id));
        String sqlQuery = "delete  from location where id = ?";
        executeQuery(sqlQuery, params);
    }

    @Override
    protected List<String> getEntityParameters(Location entity) {
        String country = entity.getCountry();
        String city = entity.getCity();
        String zone = String.valueOf(entity.getZone());

        return Arrays.asList(country, city, zone);
    }

    @Override
    protected Location buildEntity(ResultSet result) throws DAOException {
        try {
            int id = result.getInt(ID_COLUMN_LABEL);
            String country = result.getString("country");
            String city = result.getString("city");
            int zone = result.getInt("zone");

            return new Location(id, country, city, zone);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }


}
