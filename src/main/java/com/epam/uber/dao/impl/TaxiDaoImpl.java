package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.user.UserTaxi;
import com.epam.uber.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TaxiDaoImpl extends AbstractDAO<UserTaxi> {

    public List<UserTaxi> selectAllTaxi() throws DAOException {
        StringBuilder sqlQuery =
                new StringBuilder("select u.id,u.firstname,u.lastname,u.login,u.email,l.zone from user as u ")
                        .append("inner join location l on u.location = l.id where u.role = 'taxi' && u.status != 'deleted' ");
        return selectAll(sqlQuery);
    }

    public List<UserTaxi> selectAllTaxiByName(String name) throws DAOException {
        StringBuilder sqlQuery =
                new StringBuilder("select u.id,u.firstname,u.lastname,u.login,u.email,l.zone from user as u ")
                        .append("inner join location l on u.location = l.id where u.role = 'taxi' && u.status != 'deleted' ")
                        .append("&& u.firstname = '")
                        .append(name)
                        .append("'");
        return selectAll(sqlQuery);
    }

    private List<UserTaxi> selectAll(StringBuilder sqlQuery) throws DAOException {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            List<UserTaxi> taxis = new ArrayList<>();
            ResultSet result = statement.executeQuery(sqlQuery.toString());
            while (result.next()) {
                UserTaxi userTaxi = buildEntity(result);
                taxis.add(userTaxi);
            }
            return taxis;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    protected List<String> getEntityParameters(UserTaxi entity) {
        return new ArrayList<>();
    }

    @Override
    protected UserTaxi buildEntity(ResultSet result) throws DAOException {
        try {
            int id = result.getInt("id");
            String firstName = result.getString("firstname");
            String lastName = result.getString("lastname");
            String login = result.getString("login");
            String email = result.getString("email");
            int zone = result.getInt("zone");
            return new UserTaxi(id, firstName, lastName, login, email, zone);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }


}
