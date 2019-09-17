package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.CostumerDAOImpl;
import com.epam.uber.entity.client.Costumer;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.Service;

import java.sql.Connection;
import java.util.List;

public class CostumerServiceImpl implements Service<Costumer> {

    private CostumerDAOImpl costumerDAO;

    public CostumerServiceImpl(Connection connection) {
        this.costumerDAO = new CostumerDAOImpl(connection);
    }


    public int insert(Costumer costumer) throws ServiceException {
        try {
            return costumerDAO.insertCostumer(costumer);
        } catch (DAOException e) {
            throw new ServiceException("Exception during register operation.", e);
        }
    }


    public boolean delete(int id) throws ServiceException {
        try {
            return costumerDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during delete operation.", e);
        }
    }

    public List<Costumer> selectAll() throws ServiceException {
        try {
            return costumerDAO.selectAllCostumer();
        } catch (DAOException e) {
            throw new ServiceException("Exception during selectAll operation.", e);
        }
    }
}
