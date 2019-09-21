package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.client.Costumer;
import com.epam.uber.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CostumerDAOImpl extends AbstractDAO<Costumer> {

    public CostumerDAOImpl(Connection connection) {
        super(connection, "costumer");
    }

    public int insertCostumer(Costumer costumer) throws DAOException {
        String fields = "(name, phone,email,note)";
        return insert(costumer,fields);
    }

    @Override
    public List<String> getEntityParameters(Costumer entity) {
        String name = entity.getName();
        String phone = entity.getPhone();
        String email = entity.getEmail();
        String note = entity.getNote();
        note = (note == null) ? "" : note;

        return new ArrayList<>(Arrays.asList(name, phone, email, note));
    }

    @Override
    public Costumer buildEntity(ResultSet result) throws DAOException {
        try {
            int id = result.getInt(ID_COLUMN_LABEL);
            String name = result.getString("name");
            String phone = result.getString("phone");
            String email = result.getString("email");
            String note = result.getString("note");

            return new Costumer(id, name, phone, email, note);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

}
