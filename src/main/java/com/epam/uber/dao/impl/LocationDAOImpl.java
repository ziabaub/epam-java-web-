package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.Location;
import com.epam.uber.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LocationDAOImpl extends AbstractDAO<Location> {


    public LocationDAOImpl(Connection connection) {
        super(connection, "location");
    }

    public Location getLocationById(int id) throws DAOException {
        String sqlQuery = "SELECT * FROM location WHERE id=?";
        List<String> params = Collections.singletonList(String.valueOf(id));
        return getEntity(sqlQuery, params);
    }

    public boolean updateLocation(Location location) throws DAOException {
        String sqlQuery = "UPDATE location SET country=?, city=?, zone=? WHERE id =?";
        return update(location, sqlQuery);
    }

    public int insertLocation(Location location) throws DAOException {
        String fields = "(country,city,zone)";
        return insert(location, fields);
    }

    @Override
    public List<String> getEntityParameters(Location entity) {
        String country = entity.getCountry();
        String city = entity.getCity();
        String zone = String.valueOf(entity.getZone());

        return new ArrayList<>(Arrays.asList(country, city, zone));
    }

    @Override
    public Location buildEntity(ResultSet result) throws DAOException {
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
