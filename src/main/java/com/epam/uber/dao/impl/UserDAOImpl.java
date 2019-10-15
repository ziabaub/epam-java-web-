package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserDAOImpl extends AbstractDAO<User> {

    public User login(String login, String password) throws DAOException {
        String sqlQuery = "SELECT * FROM user WHERE login=? AND password=? AND status !='deleted' ";
        List<String> params = Arrays.asList(login, password);
        return getEntity(sqlQuery, params);
    }

    public void updateStatus(User user) throws DAOException {
        String sqlQuery = "UPDATE user SET status=? WHERE id=?";
        String status = user.getStatus();
        String id = String.valueOf(user.getId());
        List<String> params = Arrays.asList(status, id);
        executeQuery(sqlQuery, params);
    }

    public void updateLocation(User user) throws DAOException {
        String sqlQuery = "UPDATE user SET location =?,status=? WHERE id=?";
        String currLocation = String.valueOf(user.getLocation());
        String status = user.getStatus();
        String id = String.valueOf(user.getId());
        List<String> params = Arrays.asList(currLocation, status, id);
        executeQuery(sqlQuery, params);
    }

    public boolean containsLogin(String login) throws DAOException {
        String sqlQuery = "SELECT * FROM user WHERE login=?";
        List<String> params = Collections.singletonList(login);
        return getEntity(sqlQuery, params) == null;
    }

    public boolean containsEmail(String email) throws DAOException {
        String sqlQuery = "SELECT * FROM user WHERE email=?";
        List<String> params = Collections.singletonList(email);
        return getEntity(sqlQuery, params) == null;
    }

    public boolean containsPhone(String phone) throws DAOException {
        String sqlQuery = "SELECT * FROM user WHERE phone=?";
        List<String> params = Collections.singletonList(phone);
        return getEntity(sqlQuery, params) == null;
    }

    public void insertUser(User user) throws DAOException {
        String fields = "insert into user (location ,firstname, lastname ,login, password, email, phone, role, status) values(?,?,?,?,?,?,?,?,?)";
        insert(user, fields);
    }

    public void deleteUserById(int id) throws DAOException {
        String sqlQuery = "update user set status = 'deleted' where id = ?";
        List<String> params = Collections.singletonList(String.valueOf(id));
        executeQuery(sqlQuery, params);
    }

    @Override
    protected List<String> getEntityParameters(User entity) {

        String location = String.valueOf(entity.getLocation());
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();
        String login = entity.getLogin();
        String password = entity.getPassword();
        String email = entity.getEmail();
        String phone = entity.getPhone();
        String role = entity.getRole();
        String status = entity.getStatus();
        return Arrays.asList(location, firstName, lastName, login, password, email, phone, role, status);
    }

    @Override
    protected User buildEntity(ResultSet result) throws DAOException {
        try {
            User user = new User();
            int id = result.getInt(ID_COLUMN_LABEL);
            user.setId(id);
            int zone = result.getInt("location");
            user.setLocation(zone);
            String firstName = result.getString("firstname");
            user.setFirstName(firstName);
            String lastName = result.getString("lastname");
            user.setLastName(lastName);
            String login = result.getString("login");
            user.setLogin(login);
            String password = result.getString("password");
            user.setPassword(password);
            String email = result.getString("email");
            user.setEmail(email);
            String phone = result.getString("phone");
            user.setPhone(phone);
            String role = result.getString("role");
            user.setRole(role);
            String status = result.getString("status");
            user.setStatus(status);

            return user;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

}
