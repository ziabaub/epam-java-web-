package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.user.Author;
import com.epam.uber.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AuthorizationDAOImpl extends AbstractDAO<Author> {

    public AuthorizationDAOImpl(Connection connection) {
        super(connection, "confirmations_code");
    }

    public Author containsAuthorization(String code) throws DAOException {
        String sqlQuery = "SELECT * FROM confirmations_code WHERE code=?";
        List<String> params = Collections.singletonList(code);
        return getEntity(sqlQuery, params);
    }

    public int insertAuthor(Author author) throws DAOException {
        String fields = "(code, user_role)";
        return insert(author, fields);
    }

    @Override
    public List<String> getEntityParameters(Author entity) {
        String code = entity.getCode();
        String role = (entity.getRole()) ? "1" : "0";

        return new ArrayList<>(Arrays.asList(code, role));
    }

    @Override
    public Author buildEntity(ResultSet result) throws DAOException {
        try {
            int id = result.getInt(ID_COLUMN_LABEL);
            String code = result.getString("code");
            boolean role = result.getBoolean("user_role");

            return new Author(id, code, role);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

}
