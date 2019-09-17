package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserDAOImpl extends AbstractDAO<User> {


    public UserDAOImpl(Connection connection) {
        super(connection, "user");
    }


    public User login(String login, String password) throws DAOException {
        String sqlQuery = "SELECT * FROM user WHERE login=? AND password=?";
        List<String> params = Arrays.asList(login, password);
        return getEntity(sqlQuery, params);
    }

    public boolean isUnique(String login) throws DAOException {
        String sqlQuery = "SELECT * FROM user WHERE login=?";
        List<String> params = Collections.singletonList(login);
        return getEntity(sqlQuery, params) == null;
    }

    public User getUserById(int id) throws DAOException {
        String sqlQuery = "SELECT * FROM user WHERE id=?";
        List<String> params = Collections.singletonList(String.valueOf(id));
        return getEntity(sqlQuery, params);
    }

    public List<User> selectAllUsers() throws DAOException {
        String sqlQuery = "SELECT * FROM user";
        return getEntities(sqlQuery);
    }

    public int insertUser(User user) throws DAOException {
        String fields = "(firstname, lastname ,login, password,email,user_role)";
        return insert(user, fields);
    }

    @Override
    public List<String> getEntityParameters(User entity) {

        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();
        String login = entity.getLogin();
        String password = entity.getPassword();
        String email = entity.getEmail();
        String role = String.valueOf(entity.getUserRole());
        return new ArrayList<>(Arrays.asList(firstName, lastName, login, password, email, role));
    }

    @Override
    public User buildEntity(ResultSet result) throws DAOException {
        try {
            int id = result.getInt(ID_COLUMN_LABEL);
            String firstName = result.getString("firstname");
            String lastName = result.getString("lastname");
            String login = result.getString("login");
            String password = result.getString("password");
            String email = result.getString("email");
            boolean userRole = result.getBoolean("user_role");

            return new User(id, firstName, lastName, login, password, email, userRole);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

}
