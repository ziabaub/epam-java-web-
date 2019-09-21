package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.entity.user.UserTaxi;
import com.epam.uber.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaxiDaoImpl extends AbstractDAO<Taxi> {
    private final Connection connection;

    public TaxiDaoImpl(Connection connection) {
        super(connection, "taxi");
        this.connection = connection;
    }

    public void updateTaxi(Taxi taxi) throws DAOException {
        String sqlQuery = "UPDATE taxi SET current_location_id=?,status=? WHERE id=?";
        String currLocation = String.valueOf(taxi.getLocationId());
        String status = (taxi.getStatus()) ? "1" : "0";
        String id = String.valueOf(taxi.getId());
        List<String> params = Arrays.asList(currLocation, status, id);
        executeQuery(sqlQuery, params);
    }

    public List<UserTaxi> selectAllTaxi() throws DAOException {
        StringBuilder sqlQuery =
                new StringBuilder("select u.id ,u.firstname,u.lastname,u.login,u.email,l.zone from user as u ")
                        .append("inner join taxi as t on u.id = t.id ")
                        .append("inner join location as l on l.id=t.current_location_id order by u.id ");
        return getUserTaxis(sqlQuery);
    }

    public List<UserTaxi> selectAllTaxiByName(String name) throws DAOException {
        StringBuilder sqlQuery =
                new StringBuilder("select u.id ,u.firstname,u.lastname,u.login,u.email,l.zone from user as u ")
                        .append("inner join taxi as t on u.id = t.id ")
                        .append("inner join location as l on l.id=t.current_location_id ")
                        .append("where u.firstname = '")
                        .append(name)
                        .append("' and u.user_role = 0 order by u.id");
        return getUserTaxis(sqlQuery);
    }

    private List<UserTaxi> getUserTaxis(StringBuilder sqlQuery) throws DAOException {
        try {
            Statement statement = connection.createStatement();
            List<UserTaxi> taxis = new ArrayList<>();
            ResultSet result = statement.executeQuery(sqlQuery.toString());
            while (result.next()) {
                int id = result.getInt(ID_COLUMN_LABEL);
                String firstName = result.getString("firstname");
                String lastName = result.getString("lastname");
                String login = result.getString("login");
                String email = result.getString("email");
                int zone = result.getInt("zone");
                taxis.add(new UserTaxi(id, firstName, lastName, login, email, zone));
            }
            return taxis;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public int insertTaxi(Taxi taxi) throws DAOException {
        String fields = "(id, current_location_id,status)";
        return insert(taxi, fields);
    }

    @Override
    public List<String> getEntityParameters(Taxi entity) {
        Integer currentLocationId = entity.getLocationId();
        String locationId = String.valueOf(currentLocationId);
        String state = (entity.getStatus()) ? "1" : "0";
        String id = String.valueOf(entity.getId());
        return new ArrayList<>(Arrays.asList(id, locationId, state));
    }

    @Override
    public Taxi buildEntity(ResultSet result) throws DAOException {
        try {
            int id = result.getInt(ID_COLUMN_LABEL);
            int locationId = result.getInt("current_location_id");
            boolean state = result.getBoolean("status");

            return new Taxi(id, locationId, state);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }


}
